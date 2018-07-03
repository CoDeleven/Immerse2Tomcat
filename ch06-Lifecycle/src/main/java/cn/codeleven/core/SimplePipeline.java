package cn.codeleven.core;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/29
 */
public class SimplePipeline implements Pipeline,Lifecycle {
    private Valve basicValve;
    private List<Valve> valves = new ArrayList<>();
    @Override
    public Valve getBasic() {
        return this.basicValve;
    }

    @Override
    public void setBasic(Valve valve) {
        this.basicValve = valve;
    }

    @Override
    public void addValve(Valve valve) {
        this.valves.add(valve);
    }

    @Override
    public Valve[] getValves() {
        return new Valve[0];
    }
    private SimpleWrapperValveContext valveContext = new SimpleWrapperValveContext();
    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        this.valveContext.invokeNext(request, response);
    }

    @Override
    public void removeValve(Valve valve) {
        this.valves.remove(valve);
    }

    private List<LifecycleListener> lifecycleListeners = new ArrayList<>();
    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        this.lifecycleListeners.add(listener);
    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return lifecycleListeners.toArray(new LifecycleListener[0]);
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        lifecycleListeners.remove(listener);
    }

    @Override
    public void start() throws LifecycleException {

    }

    @Override
    public void stop() throws LifecycleException {

    }

    /**
     * class info
     * Author: CoDeleven
     * Date: 2018/6/29
     */
    public class SimpleWrapperValveContext implements ValveContext {
        private int count = 0;
        @Override
        public String getInfo() {
            return null;
        }

        @Override
        public void invokeNext(Request request, Response response) throws IOException, ServletException {
            Valve valve;

            if(count < valves.size()){
                valve = valves.get(count);
                ++count;
            }else{
                valve = basicValve;
            }

            valve.invoke(request, response, this);

        }
    }
}
