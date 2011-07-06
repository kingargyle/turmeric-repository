xquery version "1.0";
(:
ASSERTION RULE - WSDL Service names must start with upper case
:)
declare boundary-space preserve;
declare copy-namespaces no-preserve, inherit;

declare namespace wsdl = "http://schemas.xmlsoap.org/wsdl/";
declare namespace xs    = "http://www.w3.org/2001/XMLSchema";

declare variable $assertionName := "ServiceNameUppercase";
declare variable $messageTemplate := 'Service name "##" does not start with uppercase';
declare variable $FILE_PATH as document-node() external;

declare function local:fmtAssertionMessage($messageTemplate as xs:string, $errData as xs:string*) as element()
{
    let $segments := fn:tokenize($messageTemplate, "##")
    let $cntSeg := fn:count($segments)
    let $cntErr := fn:count($errData)
    let $message := fn:string-join(
        for $i in (1 to $cntSeg)
            return ($segments[$i],
                if (fn:exists($errData[$i])) then $errData[$i]
                    else if ($i lt $cntSeg) then ("?") else ()), '')
    return <assertionMessage>{$message}</assertionMessage>
};


let $doc := $FILE_PATH
let $serviceCount := fn:count(fn:distinct-values($doc//wsdl:service/@name))
return
<assertion name="{$assertionName}">
{
  if ($serviceCount eq 0)
  then local:fmtAssertionMessage("No wsdl:service elements are defined", ())
  else
    for $serviceName in fn:distinct-values($doc//wsdl:service/@name)
    return if (fn:substring($serviceName,1,1) eq fn:lower-case(fn:substring($serviceName,1,1)))
      then local:fmtAssertionMessage($messageTemplate,($serviceName))
      else ()
}</assertion>