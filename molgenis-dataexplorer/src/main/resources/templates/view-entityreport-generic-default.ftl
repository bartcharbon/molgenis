<#-- modal header -->
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title">DataSet: ${entityType.getLabel()?html}</h4>
</div>

<#-- modal body -->
<div class="modal-body">
    <div class="control-group form-horizontal">
    <#-- Generic entity information split into three columns -->
        <table class="table">
            <tbody>
            <tr>
                <th>Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
            <#list entity.getEntityType().getAtomicAttributes() as atomicAttribute>
                <#if atomicAttribute.visible>
                    <#assign key = atomicAttribute.getName()>
                    <#assign label = atomicAttribute.getLabel()>
                    <th>&nbsp;&nbsp;${label?html}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                </#if>
            </#list>
            </tr>
            <tr>
                <td>${entity.get("changeDate")?datetime}</td>
            <#list entity.getEntityType().getAtomicAttributes() as atomicAttribute>
                <#if atomicAttribute.visible>
                    <#assign key = atomicAttribute.getName()>
                    <#if entity.get(key)??>
                        <#assign type = atomicAttribute.getDataType()>
                        <td>
                            &nbsp;&nbsp;<#if type == "CATEGORICAL_MREF" || type == "MREF" || type == "ONE_TO_MANY"><#list entity.getEntities(key) as entity>${entity.getLabelValue()!?html}<#sep>
                            , </#sep></#list>
                        <#elseif type == "CATEGORICAL" || type == "FILE" || type == "XREF"><#if entity.getEntity(key)??>${entity.getEntity(key).getLabelValue()!?html}</#if>
                        <#elseif type == "BOOL">${entity.getBoolean(key)?c}
                        <#elseif type == "DATE" || type == "DATE_TIME">${entity.get(key)?datetime}
                        <#else>${entity.get(key)!?html}</#if></td>

                    <#else>
                        <td>&nbsp;</td>
                    </#if>
                </#if>
            </#list>
            </tr>
            <#if entity.get("HistorySelfReference")??>
                <#list entity.getEntities("HistorySelfReference") as entity>
                <tr>
                    <td>${entity.get("changeDate")?datetime}</td>
                    <#list entity.getEntityType().getAtomicAttributes() as atomicAttribute>
                        <#if atomicAttribute.visible>
                            <#assign key = atomicAttribute.getName()>
                            <#if entity.get(key)??>
                                <#assign type = atomicAttribute.getDataType()>
                                <td>
                                    &nbsp;&nbsp;<#if type == "CATEGORICAL_MREF" || type == "MREF" || type == "ONE_TO_MANY"><#list entity.getEntities(key) as entity>${entity.getLabelValue()!?html}<#sep>
                                    , </#sep></#list>
                                <#elseif type == "CATEGORICAL" || type == "FILE" || type == "XREF"><#if entity.getEntity(key)??>${entity.getEntity(key).getLabelValue()!?html}</#if>
                                <#elseif type == "BOOL">${entity.getBoolean(key)?c}
                                <#elseif type == "DATE" || type == "DATE_TIME">${entity.get(key)?datetime}
                                <#else>${entity.get(key)!?html}</#if></td>

                            <#else>
                                <td>&nbsp;</td>
                            </#if>
                        </#if>
                    </#list>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</div>

<#-- modal footer -->
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">close</button>
</div>