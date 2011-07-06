module namespace assertion="http://ebay.com/repositorymanager/assertion";

declare copy-namespaces no-preserve, inherit;

declare function assertion:fmtAssertionMessage(
   $messageTemplate as xs:string,
    $errData as xs:string*) as xs:string
{
    let $segments := fn:tokenize($messageTemplate, "##")
    let $cntSeg := fn:count($segments)
    let $cntErr := fn:count($errData)
    let $message := fn:string-join(
        for $i in (1 to $cntSeg)
            return ($segments[$i],
                if (fn:exists($errData[$i])) then $errData[$i]
                    else if ($i lt $cntSeg) then ("?") else ()), '')
    return $message
};

declare function assertion:getAssertionResultItem(
    $messageTemplate as xs:string,
    $errData as xs:string*) as element()
{
    let $message := assertion:fmtAssertionMessage($messageTemplate, $errData)
    return <AssertionResultItem>{$message}</AssertionResultItem>
};

declare function assertion:getAssertionResultItem(
    $messageTemplate as xs:string,
    $artifactName as xs:string,
    $errData as xs:string*) as element()
{
    let $message := assertion:fmtAssertionMessage($messageTemplate, $errData)
    return <AssertionResultItem artifact="{$artifactName}">{$message}</AssertionResultItem>
};

declare function assertion:getAssertionResultItem(
    $messageTemplate as xs:string,
    $artifactName as xs:string,
    $lineNumber as xs:integer,
    $errData as xs:string*) as element()
{
    let $message := assertion:fmtAssertionMessage($messageTemplate, $errData)
    return <AssertionResultItem artifact-name="{$artifactName}" line-number="{$lineNumber}">XYZ {$artifactName} {$lineNumber} {$message}</AssertionResultItem>
};
