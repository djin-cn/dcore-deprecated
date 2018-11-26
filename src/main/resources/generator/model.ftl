package ${package};

/**
 * @author:djin
 * @date: ${.now}
*/
public class ${tableClass.shortClassName}${mapperSuffix} {
<#if tableClass.pkFields??>
<#list tableClass.pkFields as field>
    <#if field.fullTypeName == "java.lang.Short">
        <#assign types="java.lang.Integer" />
    <#else>
        <#assign types=field.fullTypeName />
    </#if>
    /**
     * ${field.remarks}
     */
    @javax.persistence.Id
    private ${types} ${field.fieldName};
</#list>
</#if>
<#if tableClass.baseFields??>
<#list tableClass.baseFields as field>
    <#if field.fullTypeName == "java.lang.Short">
        <#assign types="java.lang.Integer" />
    <#else>
        <#assign types=field.fullTypeName />
    </#if>
    /**
     * ${field.remarks}
     */
    private ${types} ${field.fieldName};
</#list>
</#if>

<#if tableClass.allFields??>
<#list tableClass.allFields as field>
    <#if field.fullTypeName == "java.lang.Short">
        <#assign types="java.lang.Integer" />
    <#else>
        <#assign types=field.fullTypeName />
    </#if>
    <#if field.remarks??>
    /**
     * ${field.remarks}
     */
    </#if>
    public ${types} get${field.fieldName?cap_first}(){
        return ${field.fieldName};
    }
    <#if field.remarks??>
    /**
     * ${field.remarks}
     */
    </#if>
    public void set${field.fieldName?cap_first}(${types} ${field.fieldName}){
        this.${field.fieldName} = ${field.fieldName};
    }
</#list>
</#if>
}