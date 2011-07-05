xquery version "1.0";
(:
ASSERTION RULE - If an element name ends with the word "Count" and is not of type "xs:int", warning should be issued
:)
import module namespace rmassert="http://ebay.com/repository/assertion" at "/assertion/TestModule/1-0/FILE_PATH";

declare boundary-space preserve;
declare copy-namespaces no-preserve, inherit;

declare namespace wsdl="http://schemas.xmlsoap.org/wsdl/";
declare namespace xs="http://www.w3.org/2001/XMLSchema";

declare variable $assertionName     := "ElementNameEndingWithWordCount";
declare variable $failureTemplate     := 'Element "##", having its name ending with the word "Count" should not be of type otherthan "xs:int"';
declare variable $FILE_PATH as document-node() external;

let $doc := $FILE_PATH
let $elements := $doc//wsdl:types//xs:element
return
<assertion name="{$assertionName}">
{
	for $element in $elements 
		let $name                 := $element/@name
		let $type                   := if(string-length(substring-after($element/@type, ":")) gt 0) 
										    then (substring-after($element/@type, ":"))
											else ($element/@type)	
		return
			if (substring($name, string-length($name) - 4) eq "Count")
			then
				if ($type eq "int")
				then ()
				else (rmassert:fmtAssertionMessage($failureTemplate, $name))
			else()
}
</assertion>
