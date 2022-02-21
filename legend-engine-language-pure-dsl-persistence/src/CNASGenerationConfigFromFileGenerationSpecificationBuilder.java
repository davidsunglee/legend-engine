package org.finos.legend.engine.external.language.cnas.extension;

import org.finos.legend.engine.external.format.cnas.schema.generations.CNASGenerationConfig;
import org.finos.legend.engine.language.pure.dsl.generation.config.ConfigBuilder;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.fileGeneration.FileGenerationSpecification;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.fileGeneration.FileGenerationType;
import org.finos.legend.engine.shared.core.operational.Assert;

public class CNASGenerationConfigFromFileGenerationSpecificationBuilder
{
    public static CNASGenerationConfig build(FileGenerationSpecification fileGeneration)
    {
        Assert.assertTrue(fileGeneration.type.equals(FileGenerationType.cnas.name()), () -> "File generation of type of cnas expected, got '" + fileGeneration.type + "'");
        CNASGenerationConfig cnasConfig = new CNASGenerationConfig();
        ConfigBuilder.duplicateCheck(fileGeneration.configurationProperties);
        //ConfigBuilder.noConfigurationPropertiesCheck(fileGeneration);
        ConfigBuilder.setScopeElements(fileGeneration, cnasConfig);
        fileGeneration.configurationProperties.forEach(e -> ConfigBuilder.setConfigurationProperty(fileGeneration, e, cnasConfig));
        return cnasConfig;
    }
}