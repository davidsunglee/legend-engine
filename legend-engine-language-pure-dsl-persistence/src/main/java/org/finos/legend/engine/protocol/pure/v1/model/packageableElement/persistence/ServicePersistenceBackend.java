package org.finos.legend.engine.protocol.pure.v1.model.packageableElement.persistence;

import org.finos.legend.engine.protocol.pure.v1.model.context.PackageableElementPointer;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.PackageableElement;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.PackageableElementVisitor;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.persistence.backend.Backend;

public class ServicePersistenceBackend extends PackageableElement {
    public PackageableElementPointer servicePersistence;
    public Backend backend;

    @Override
    public <T> T accept(PackageableElementVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
