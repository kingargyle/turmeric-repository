xquery version "1.0";
module namespace rmassert="http://ebay.com/repository/assertion";

declare copy-namespaces no-preserve, inherit;

declare function rmassert:fmtAssertionMessage($messageTemplate as xs:string, $errData as xs:string*) as element()
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