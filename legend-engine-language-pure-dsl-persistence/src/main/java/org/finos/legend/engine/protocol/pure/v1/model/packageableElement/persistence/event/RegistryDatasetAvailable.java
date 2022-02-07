package org.finos.legend.engine.protocol.pure.v1.model.packageableElement.persistence.event;

public class RegistryDatasetAvailable extends EventType
{
    @Override
    public <T> T accept(EventTypeVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
}