package org.finos.legend.engine.external.format.cnas.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.finos.legend.engine.external.format.cnas.extension.CNASGenerationConfigFromFileGenerationSpecificationBuilder;
import org.finos.legend.engine.external.format.cnas.schema.generations.CNASGenerationConfig;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.PureModel;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.fileGeneration.FileGenerationSpecification;
import org.finos.legend.engine.shared.core.ObjectMapperFactory;
import org.finos.legend.engine.shared.core.deployment.DeploymentMode;
import org.finos.legend.pure.generated.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class TestCNASFileGeneration
{
    @Test
    public void testSimpleCNAS()
    {
        try
        {
            PureModelContextData pureModelContextData = getProtocol("simpleFileGeneration.json");
            PureModel pureModel = new PureModel(pureModelContextData, null, DeploymentMode.TEST);
            FileGenerationSpecification fileGeneration = pureModelContextData.getElementsOfType(FileGenerationSpecification.class).get(0);
            CNASGenerationConfig cnasConfig = CNASGenerationConfigFromFileGenerationSpecificationBuilder.build(fileGeneration);
            Root_meta_external_format_cnas_generation_CNASConfig metaModelConfig = cnasConfig.process(pureModel);
            List<? extends Root_meta_pure_generation_metamodel_GenerationOutput> outputs = core_external_format_cnas_transformation_cnasSchemaGenerator.Root_meta_external_format_cnas_generation_generateCNASFromPureWithScope_CNASConfig_1__CNASOutput_MANY_(metaModelConfig, pureModel.getExecutionSupport()).toList();
            Assert.assertEquals(outputs.size(), 4); // size - ?
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private PureModelContextData getProtocol(String fileName) throws JsonProcessingException
    {
        String jsonString = this.getResourceAsString(fileName);
        return ObjectMapperFactory.getNewStandardObjectMapperWithPureProtocolExtensionSupports().readValue(jsonString, PureModelContextData.class);
    }

    private String getResourceAsString(String fileName)
    {
        InputStream inputStream = TestCNASFileGeneration.class.getResourceAsStream(fileName);
        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}