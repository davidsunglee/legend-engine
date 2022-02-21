package org.finos.legend.engine.external.language.cnas.extension;

import org.eclipse.collections.api.RichIterable;
import org.finos.legend.engine.external.language.cnas.schema.generations.CNASGenerationConfig;
import org.finos.legend.engine.external.language.cnas.schema.generations.CNASGenerationService;
import org.finos.legend.engine.external.shared.format.extension.GenerationExtension;
import org.finos.legend.engine.external.shared.format.extension.GenerationMode;
import org.finos.legend.engine.external.shared.format.generations.description.FileGenerationDescription;
import org.finos.legend.engine.external.shared.format.generations.description.GenerationConfigurationDescription;
import org.finos.legend.engine.external.shared.format.generations.description.GenerationProperty;
import org.finos.legend.engine.external.shared.format.imports.description.ImportConfigurationDescription;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.CompileContext;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.PureModel;
import org.finos.legend.engine.language.pure.modelManager.ModelManager;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.PackageableElement;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.fileGeneration.FileGenerationSpecification;
import org.finos.legend.pure.generated.*;

import java.util.ArrayList;
import java.util.List;

public class CNASGenerationExtension implements GenerationExtension
{
    @Override
    public String getLabel()
    {
        return "CNAS";
    }

    @Override
    public String getKey()
    {
        return "cnas";
    }

    @Override
    public GenerationMode getMode()
    {
        return GenerationMode.Schema;
    }

    @Override
    public GenerationConfigurationDescription getGenerationDescription()
    {
        return new GenerationConfigurationDescription()
        {
            @Override
            public String getLabel()
            {
                return CNASGenerationExtension.this.getLabel();
            }

            @Override
            public String getKey()
            {
                return CNASGenerationExtension.this.getKey();
            }

            @Override
            public List<GenerationProperty> getProperties(PureModel pureModel)
            {
                return FileGenerationDescription.extractGenerationProperties(core_external_language_cnas_integration.Root_meta_external_format_cnas_generation_describeConfiguration__GenerationParameter_MANY_(pureModel.getExecutionSupport()));
            }
        };
    }

    @Override
    public ImportConfigurationDescription getImportDescription()
    {
        return null;
    }

    @Override
    public Root_meta_pure_generation_metamodel_GenerationConfiguration defaultConfig(CompileContext context)
    {
        return core_external_format_cnas_integration.Root_meta_external_format_cnas_generation_defaultConfig__CNASConfig_1_(context.pureModel.getExecutionSupport());
    }

    @Override
    public Object getService(ModelManager modelManager)
    {
        return new CNASGenerationService(modelManager);
    }

    @Override
    public List<Root_meta_pure_generation_metamodel_GenerationOutput> generateFromElement(PackageableElement element, CompileContext compileContext)
    {
        if (element instanceof FileGenerationSpecification)
        {
            FileGenerationSpecification specification = (FileGenerationSpecification) element;
            CNASGenerationConfig cnasConfig = CNASGenerationConfigFromFileGenerationSpecificationBuilder.build(specification);
            RichIterable<? extends Root_meta_pure_generation_metamodel_GenerationOutput> output = core_external_format_cnas_transformation_cnasSchemaGenerator.Root_meta_external_format_cnas_generation_generateCNASFromPureWithScope_CNASConfig_1__CNASOutput_MANY_(cnasConfig.process(compileContext.pureModel), compileContext.pureModel.getExecutionSupport());
            return new ArrayList<>(output.toList());
        }
        return null;
    }
}