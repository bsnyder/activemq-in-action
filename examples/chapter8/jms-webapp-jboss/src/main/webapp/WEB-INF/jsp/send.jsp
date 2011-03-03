<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
<head>
<title>JMS Message Sender</title>
<script language="javascript">
hex = 0
function fadeOutText() { 
  if(hex < 255) { 
    hex += 11; 
    document.getElementById("message").style.color="rgb(" + hex + "," + hex + "," + hex + ")";
    setTimeout("fadeOutText()",40); 
  }
  else
    hex = 0 
}
</script>
</head>
<body onload="fadeOutText()">

<h3>Send a JMS Message</h3>
<form:form action="send.html" modelAttribute="jmsMessageBean">
    <table>
      <tr>
        <td align="center" colspan="2">&nbsp;
	    <c:if test="${not empty successfulSend}">
	        <div id="message">${successfulSend}</div>
	    </c:if>
        </td>
      </tr>
      <tr>
        <td align="right">
	      <b>Reply To:</b>
        </td>
        <td>
          <form:input path="replyTo" />
        </td>
      </tr>
      <tr>
        <td align="right">
	      <b>Time To Live:</b>
        </td>
        <td>
          <form:input path="timeToLive" />
        </td>
      </tr>
      <tr>
        <td align="right">
	      <b>Persistent:</b>
        </td>
        <td>
          <form:checkbox path="persistent" />
        </td>
      </tr>
      <tr>
        <td align="right">
	      <b>Message:</b>
        </td>
        <td>
          <form:textarea rows="10" cols="30" path="messagePayload" />
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="submit" value="Send Message" />
        </td>
      </tr>
    </table>
</form:form>
</body>
</html>