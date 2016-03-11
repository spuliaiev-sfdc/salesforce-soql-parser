package org.mule.soql.query.clause;

import org.mule.soql.query.ObjectSpec;
import org.mule.soql.query.SOQLData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by damianpelaez on 3/6/16.
 */
public class FromClause extends SOQLData {
    private List<ObjectSpec> relationObjectSpecs = new ArrayList<ObjectSpec>();
    private ObjectSpec mainObjectSpec;

    public FromClause() {
    }

    public FromClause(ObjectSpec mainObjectSpec) {
        this.mainObjectSpec = mainObjectSpec;
    }

    @Override
    public String toSOQLText() {
        StringBuilder sb = new StringBuilder();

        sb.append("FROM").append(" ");

        if(mainObjectSpec != null) {
            sb.append(mainObjectSpec.toSOQLText());
        }

        if(relationObjectSpecs != null) {
            for(ObjectSpec relationObjectSpec : relationObjectSpecs) {
                sb.append(",").append(relationObjectSpec.toSOQLText());
            }
        }

        return sb.toString();
    }

    public void addRelationObjectSpec(ObjectSpec relationObjectSpec) {
        if(relationObjectSpec == null) { return; }

        if(relationObjectSpecs == null) {
            relationObjectSpecs = new ArrayList<ObjectSpec>();
        }

        relationObjectSpecs.add(relationObjectSpec);
    }

    public ObjectSpec getMainObjectSpec() {
        return mainObjectSpec;
    }

    public void setMainObjectSpec(ObjectSpec mainObjectSpec) {
        this.mainObjectSpec = mainObjectSpec;
    }

    public List<ObjectSpec> getRelationObjectSpecs() {
        return relationObjectSpecs;
    }

}