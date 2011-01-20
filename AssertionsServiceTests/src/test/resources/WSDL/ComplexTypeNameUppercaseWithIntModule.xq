xquery version "1.0";
(:
ASSERTION RULE - WSDL complex type names must start with upper case
:)

declare boundary-space preserve;
declare copy-namespaces no-preserve, inherit;

declare namespace wsdl="http://schemas.xmlsoap.org/wsdl/";
declare namespace xs="http://www.w3.org/2001/XMLSchema";

import module namespace rmassert="http://ebay.com/repository/assertion" at "/assertion/soa_assertionFrameworkUtil_assertionModule/1-0-0/Assertion-Modelue-Script";

declare variable $assertionName := "ComplexTypeNameUppercase";
declare variable $failureTemplate := 'Complex type name "##" does not start with uppercase';
declare variable $successTemplate := 'Complex type name "##" starts with uppercase';
declare variable $FILE_PATH as document-node() external;

let $doc := $FILE_PATH
return
<assertion name="{$assertionName}">
{
let $elementAssertion := 
	for $element in $doc//wsdl:types/xs:schema/xs:element
	return 
		if (exists($element/xs:complexType) and (fn:substring($element/@name,1,1) eq fn:lower-case(fn:substring($element/@name,1,1))))
		then rmassert:fmtAssertionMessage($failureTemplate,($element/@name))
	    else ()
	    
let $complexTypeAssertion := 
	for $complexType in $doc//wsdl:types/xs:schema/xs:complexType
	return 
		if (fn:substring($complexType/@name,1,1) eq fn:lower-case(fn:substring($complexType/@name,1,1)))
		then rmassert:fmtAssertionMessage($failureTemplate,($complexType/@name))
	    else ()

return ($elementAssertion, $complexTypeAssertion)
}</assertion>