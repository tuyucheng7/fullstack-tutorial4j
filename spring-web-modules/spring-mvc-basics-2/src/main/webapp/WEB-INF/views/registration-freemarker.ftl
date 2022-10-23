<#import "/spring.ftl" as spring/>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>User Registration</title>
</head>
<body>
<form action="register" method="post">
    <@spring.bind path="user" />
    Email:<@spring.formInput "user.email"/> <br/>
    Password:<@spring.formPasswordInput "user.password"/> <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>