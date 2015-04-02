#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


public class BurpExtender implements IBurpExtender, IHttpListener, IProxyListener
{

    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;
    private PrintWriter stdout;
    private PrintWriter stderr;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks iBurpExtenderCallbacks)
    {
        this.callbacks = iBurpExtenderCallbacks;
        this.helpers = this.callbacks.getHelpers();

        this.callbacks.setExtensionName("Name");
        this.callbacks.registerHttpListener(this);
        this.callbacks.registerProxyListener(this);
        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        this.stderr = new PrintWriter(callbacks.getStderr(), true);
    }


    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo)
    {
        stdout.println(
                (messageIsRequest ? "HTTP request to " : "HTTP response from ") +
                        messageInfo.getHttpService() +
                        " [" + callbacks.getToolName(toolFlag) + "]");
    }

    @Override
    public void processProxyMessage(boolean messageIsRequest, IInterceptedProxyMessage message)
    {
        stdout.println(
                (messageIsRequest ? "Proxy request to " : "Proxy response from ") +
                        message.getMessageInfo().getHttpService());
    }
}
