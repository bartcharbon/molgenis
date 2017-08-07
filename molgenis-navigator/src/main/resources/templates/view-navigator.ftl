<#include "resource-macros.ftl"><#include "resource-macros.ftl">
<#include "theme-macros.ftl">
<!doctype html>
<html lang="en">
<head>
    <title>Package navigator</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="X-UA-Compatible" content="chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/js/favicon.ico">
    <link rel="stylesheet" href="<@theme_href "/css/theme/bootstrap-4/${app_settings.bootstrapTheme?html}"/>" type="text/css"
          id="bootstrap-theme">
    <link href="<@resource_href "/css/navigator/app.css" />" rel="stylesheet">
</head>
<body>
    <div id="app"></div>

    <script type="text/javascript">
        window.__INITIAL_STATE__ = {
            baseUrl: '${baseUrl}',
            lng: '${lng}',
            fallbackLng: '${fallbackLng}'
        }
    </script>

    <script type=text/javascript src="<@resource_href "/js/navigator/manifest.js"/>"></script>
    <script type=text/javascript src="<@resource_href "/js/navigator/vendor.js"/>"></script>
    <script type=text/javascript src="<@resource_href "/js/navigator/app.js"/>"></script>
</body>
</html>