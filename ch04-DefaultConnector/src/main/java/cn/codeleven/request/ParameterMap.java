package cn.codeleven.request;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/21
 */
public class ParameterMap extends HashMap<String, String[]> {
    private boolean isLocked;

    public void setLocked(boolean locked) {
        if(isLocked){
            throw new IllegalStateException("无法修改ParameterMap...");
        }
        isLocked = locked;
    }

    @Override
    public String[] put(String key, String[] value) {
        if(isLocked){
           throw new IllegalStateException("无法修改ParameterMap...");
        }
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends String[]> m) {
        if(isLocked){
            throw new IllegalStateException("无法修改ParameterMap...");
        }
        super.putAll(m);
    }

    @Override
    public String[] putIfAbsent(String key, String[] value) {
        if(isLocked){
            throw new IllegalStateException("无法修改ParameterMap...");
        }
        return super.putIfAbsent(key, value);
    }

    @Override
    public String[] remove(Object key) {
        if(isLocked){
            throw new IllegalStateException("无法修改ParameterMap...");
        }
        return super.remove(key);
    }

    @Override
    public boolean remove(Object key, Object value) {
        if(isLocked){
            throw new IllegalStateException("无法修改ParameterMap...");
        }
        return super.remove(key, value);
    }

    @Override
    public void clear() {
        if(isLocked){
            throw new IllegalStateException("无法修改ParameterMap...");
        }
        super.clear();
    }

    @Override
    public boolean replace(String key, String[] oldValue, String[] newValue) {
        if(isLocked){
            throw new IllegalStateException("无法修改ParameterMap...");
        }
        return super.replace(key, oldValue, newValue);
    }

    @Override
    public String[] replace(String key, String[] value) {
        if(isLocked){
            throw new IllegalStateException("无法修改ParameterMap...");
        }
        return super.replace(key, value);
    }

    @Override
    public String[] merge(String key, String[] value, BiFunction<? super String[], ? super String[], ? extends String[]> remappingFunction) {
        if(isLocked){
            throw new IllegalStateException("无法修改ParameterMap...");
        }
        return super.merge(key, value, remappingFunction);
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super String[], ? extends String[]> function) {
        if(isLocked){
            throw new IllegalStateException("无法修改ParameterMap...");
        }
        super.replaceAll(function);
    }
}
