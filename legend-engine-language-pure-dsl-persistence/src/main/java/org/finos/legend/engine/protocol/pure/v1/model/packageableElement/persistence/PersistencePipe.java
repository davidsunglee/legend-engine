package org.finos.legend.engine.protocol.pure.v1.model.packageableElement.persistence;

import org.finos.legend.engine.protocol.pure.v1.model.SourceInformation;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.PackageableElement;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.PackageableElementVisitor;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.persistence.event.EventType;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.persistence.reader.Reader;

public class PersistencePipe extends PackageableElement
{
    public String documentation;
    public java.util.List<String> owners = java.util.Collections.<String>emptyList();
    public EventType trigger;
    public Reader reader;
    public Persister persister;
    public SourceInformation sourceInformation;

    @Override
    public <T> T accept(PackageableElementVisitor<T> visitor) {
        return visitor.visit(this);
    }
}