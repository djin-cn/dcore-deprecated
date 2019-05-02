package ${package};
import lombok.Data;

/**
 * @author:djin
 * @date: ${.now}
*/
@Data
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
}