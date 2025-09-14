<%@taglib prefix="s" uri="/struts-tags"%>

<p style="font-weight: bold" class="error">
   <s:property value="getText('%{exception.message}')" />
</p>
