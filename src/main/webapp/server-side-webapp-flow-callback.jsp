<%@ page import="com.clouway.Util" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.http.NameValuePair" %>
<%@ page import="org.apache.http.client.methods.HttpGet" %>
<%@ page import="org.apache.http.message.BasicNameValuePair" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.apache.http.client.HttpClient" %>
<%@ page import="org.apache.http.impl.client.DefaultHttpClient" %>
<%@ page import="org.apache.http.client.methods.HttpPost" %>
<%@ page import="java.io.IOException" %>
<%@ page import="org.apache.http.HttpResponse" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String incomingState = request.getParameter("state");
    String expectedState = (String) session.getAttribute("oauth2-state");

    if (!StringUtils.equals(incomingState, expectedState)) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid state");
        return;
    }

    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    parameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
    parameters.add(new BasicNameValuePair("client_id", "975229642235-slatste5to3kb3f0gv100qm27ril6jfs.apps.googleusercontent.com"));
    parameters.add(new BasicNameValuePair("client_secret", "o8RD7MMbADzTISr5eAl-HI99"));
    parameters.add(new BasicNameValuePair("redirect_uri", request.getRequestURL().toString()));
    parameters.add(new BasicNameValuePair("code", request.getParameter("code")));

    String output = Util.sendHttpPost("https://accounts.google.com/o/oauth2/token", parameters);
    Map<String, String> values = Util.parseJson(output);

%>

<html>
<head>
    <title></title>
</head>
<body>
<p>Returned values are : <%=values%>
</p>

<%
//    String infoService = "https://www.googleapis.com/oauth2/v1/userinfo";
//    HttpGet get = new HttpGet(infoService);
//    get.setHeader("authorization", "Bearer" + values.get("access_token"));

    HttpGet get = new HttpGet("https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token="+values.get("access_token"));
    String userInfo = Util.sendHttpRequest(get);

    try{
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("https://accounts.google.com/o/oauth2/revoke?token="+values.get("access_token"));
        HttpResponse response1 = client.execute(post);
    }
    catch(IOException e)
    {
    }
%>
<%=userInfo%>



</body>
</html>
