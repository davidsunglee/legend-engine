package org.finos.legend.engine.external.format.cnas.schema.generations;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.utility.ListIterate;
import org.finos.legend.engine.external.shared.format.generations.GenerationConfiguration;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.PureModel;
import org.finos.legend.pure.generated.Root_meta_external_format_cnas_generation_CNASConfig;
import org.finos.legend.pure.generated.core_external_format_cnas_integration;
import org.finos.legend.pure.m3.coreinstance.meta.pure.metamodel.PackageableElement;
import org.finos.legend.pure.runtime.java.compiled.generation.processors.support.map.PureMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.finos.legend.pure.generated.core_pure_corefunctions_metaExtension.Root_meta_pure_functions_meta_pathToElement_String_1__PackageableElement_1_;

public class CNASGenerationConfig extends GenerationConfiguration
{
    /**
     * Adds namespace derived from package to Avro schema. Default: true.
     */
    public Boolean includeNamespace;

    /**
     * Includes properties from super types. Default: true.
     */
    public Boolean includeSuperTypes;

    /**
     * Includes properties from associations. Default: true.
     */
    public Boolean includeAssociations;

    /**
    To do: Add other generated properties to be included.

    public Boolean include...;
    */

    /**
     * Generates properties from specified profile tags
     */
    public List<String> propertyProfile = Collections.emptyList();

    /**
     * Override namespace in generated schema
     */
    public Map<String, String> namespaceOverride = UnifiedMap.newMap();

    public Root_meta_external_format_cnas_generation_CNASConfig process(PureModel pureModel)
    {
        Root_meta_external_format_avro_generation_CNASConfig cnasConfig = core_external_format_cnas_integration.Root_meta_external_format_cnas_generation_defaultConfig__CNASConfig_1_(pureModel.getExecutionSupport());
        List<PackageableElement> scopeElements = ListIterate.collect(this.generationScope(), e -> Root_meta_pure_functions_meta_pathToElement_String_1__PackageableElement_1_(e, pureModel.getExecutionSupport()));
        cnasConfig._scopeElements((RichIterable<? extends PackageableElement>) scopeElements);
        if (includeNamespace != null)
        {
            cnasConfig._includeNamespace(includeNamespace);
        }
        if (includeSuperTypes != null)
        {
            cnasConfig._includeSuperTypes(includeSuperTypes);
        }

        if (includeAssociations != null)
        {
            cnasConfig._includeAssociations(includeAssociations);
        }

        if (!propertyProfile.isEmpty())
        {
            cnasConfig._propertyProfile(ListIterate.collect(propertyProfile, pureModel::getProfile));
        }
        PureMap _namespaceOverride = null;
        if (this.namespaceOverride != null)
        {
            UnifiedMap<String, String> modifiedQualifiers = UnifiedMap.newMap();
            this.namespaceOverride.forEach(modifiedQualifiers::put);
            _namespaceOverride = new PureMap(modifiedQualifiers);
        }
        cnasConfig._namespaceOverride(_namespaceOverride);
        return cnasConfig;
    }
}