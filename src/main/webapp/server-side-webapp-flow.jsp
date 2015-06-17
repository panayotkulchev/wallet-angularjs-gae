<%@ page import="com.clouway.Util" %>
<%@ page import="org.apache.commons.lang.RandomStringUtils" %>
<%@ page import="org.apache.http.client.utils.URIBuilder" %>
<%@ page import="java.net.URL" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String oauthURL=null;

    try {

        String state = RandomStringUtils.random(32, true, true);
        session.setAttribute("oauth2-state", state);

        URIBuilder builder = new URIBuilder("https://accounts.google.com/o/oauth2/auth");
        builder.addParameter("state", state);

        URL redirectUrl = Util.getAbsoluteUrl(request, "server-side-webapp-flow-callback.jsp");
        builder.addParameter("redirect_uri", redirectUrl.toString());

        builder.addParameter("client_id", "975229642235-slatste5to3kb3f0gv100qm27ril6jfs.apps.googleusercontent.com");
        builder.addParameter("response_type", "code");
        builder.addParameter("scope", "profile email");
        builder.addParameter("approval_prompt", "force");

        oauthURL = builder.toString();
    }catch (Exception e){

    }
%>
<html>
<head>
    <title></title>
</head>
<body>
<a href="<%=oauthURL%>">Login with google</a>
</body>
</html>
