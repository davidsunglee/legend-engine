package org.finos.legend.engine.external.language.cnas.schema.generations;

import io.opentracing.Scope;
import io.opentracing.util.GlobalTracer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.list.MutableList;
import org.finos.legend.engine.external.shared.format.generations.GenerationOutput;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.PureModel;
import org.finos.legend.engine.language.pure.modelManager.ModelManager;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.finos.legend.engine.shared.core.api.result.ManageConstantResult;
import org.finos.legend.engine.shared.core.kerberos.ProfileManagerHelper;
import org.finos.legend.engine.shared.core.operational.errorManagement.ExceptionTool;
import org.finos.legend.engine.shared.core.operational.logs.LogInfo;
import org.finos.legend.engine.shared.core.operational.logs.LoggingEventType;
import org.finos.legend.pure.generated.Root_meta_pure_generation_metamodel_GenerationOutput;
import org.finos.legend.pure.generated.core_external_format_cnas_transformation_cnasSchemaGenerator;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.jax.rs.annotations.Pac4JProfileManager;
import org.slf4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.finos.legend.engine.shared.core.operational.http.InflateInterceptor.APPLICATION_ZLIB;

@Api(tags = "Z - Deprecated - Generation - Schema")
@Path("pure/v1/schemaGeneration")
@Produces(MediaType.APPLICATION_JSON)
public class CNASGenerationService
{
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger("Alloy Execution Server");
    private final ModelManager modelManager;

    public CNASGenerationService(ModelManager modelManager)
    {
        this.modelManager = modelManager;
    }

    @POST
    @Path("cnas")
    @ApiOperation(value = "Generates CNAS schema for a given class and transitive dependencie")
    @Consumes({MediaType.APPLICATION_JSON, APPLICATION_ZLIB})
    public Response generateCNAS(CNASGenerationInput generateCNASInput, @ApiParam(hidden = true)  @Pac4JProfileManager ProfileManager<CommonProfile> pm)
    {
        MutableList<CommonProfile> profiles = ProfileManagerHelper.extractProfiles(pm);
        boolean interactive = generateCNASInput.model instanceof PureModelContextData;
        try (Scope scope = GlobalTracer.get().buildSpan("Service: Generate CNAS").startActive(true))
        {
            return exec(
                    generateCNASInput.config != null ? generateCNASInput.config : new CNASGenerationConfig(),
                    () -> this.modelManager.loadModelAndData(generateCNASInput.model, generateCNASInput.clientVersion, profiles, null).getTwo(),
                    interactive,
                    profiles);
        }
        catch (Exception ex)
        {
            return ExceptionTool.exceptionManager(ex, interactive ? LoggingEventType.GENERATE_CNAS_CODE_INTERACTIVE_ERROR : LoggingEventType.GENERATE_CNAS_CODE_ERROR, profiles);
        }
    }

    private Response exec(CNASGenerationConfig cnasConfig, Function0<PureModel> pureModelFunc, boolean interactive, MutableList<CommonProfile> pm)
    {
        try
        {
            long start = System.currentTimeMillis();
            LOGGER.info(new LogInfo(pm, interactive ? LoggingEventType.GENERATE_CNAS_CODE_INTERACTIVE_START : LoggingEventType.GENERATE_CNAS_CODE_START).toString());
            PureModel pureModel = pureModelFunc.value();
            RichIterable<? extends Root_meta_pure_generation_metamodel_GenerationOutput> output = core_external_format_cnas_transformation_cnasSchemaGenerator.Root_meta_external_format_cnas_generation_generateCNASFromPureWithScope_CNASConfig_1__CNASOutput_MANY_(cnasConfig.process(pureModel), pureModel.getExecutionSupport());
            LOGGER.info(new LogInfo(pm, interactive ? LoggingEventType.GENERATE_CNAS_CODE_INTERACTIVE_STOP : LoggingEventType.GENERATE_CNAS_CODE_STOP, (double)System.currentTimeMillis() - start).toString());
            return ManageConstantResult.manageResult(pm, output.collect(v -> new GenerationOutput(v._content(), v._fileName(), v._format())).toList());
        }
        catch (Exception ex)
        {
            return ExceptionTool.exceptionManager(ex, interactive ? LoggingEventType.GENERATE_CNAS_CODE_INTERACTIVE_ERROR : LoggingEventType.GENERATE_CNAS_CODE_ERROR, pm);
        }
    }
}