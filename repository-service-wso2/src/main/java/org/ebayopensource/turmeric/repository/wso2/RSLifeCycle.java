/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.wso2.carbon.registry.app.RemoteRegistry;
import org.wso2.carbon.registry.core.Resource;

/**
 * @author mgorovoy
 *
 */
public class RSLifeCycle {
    private static final String __lifeCycleProp = "org.ebayopensource.turmeric.repository.wso2.lifecycle";
    private static final String __lifecyclePath = "/_system/config/repository/components/org.wso2.carbon.governance/lifecycles/";
    private static final String __statePropFormat = "registry.lifecycle.%1$s.state";
    private static final String __optionPropFormat = "registry.custom_lifecycle.checklist.option%1$d.item";
    private static final String __commentPropFormat = "org.ebayopensource.turmeric.lifecycle.option%1$d.comment";
    
    private static String __name;
    private static String __stateProp;
    
    private static RSLifeCycle __baseLifeCycle;
   
    private String _state;
    private List<Item> _items;
    private LinkedHashMap<String, Integer> _map;

    static {
        try {
   
            String lcName = System.getProperty(__lifeCycleProp, "TurmericLifeCycle");

            RemoteRegistry wso2 = RSProviderUtil.getRegistry();
            Resource lifecycle = wso2.get(__lifecyclePath+lcName);
            Document lc = DocumentBuilderFactory.newInstance().
                    newDocumentBuilder().parse(lifecycle.getContentStream());
    
            Element aspectElement = lc.getDocumentElement();
            __name = aspectElement.getAttributes().getNamedItem("name").getNodeValue();
            __stateProp = String.format(__statePropFormat, __name);
     
            Element configurationElement = (Element) aspectElement.
                    getElementsByTagName("configuration").item(0);
            
            Element lifecycleElement = (Element) configurationElement.
                    getElementsByTagName("lifecycle").item(0);
            
            List<Item> initItems = new ArrayList<Item>();
            LinkedHashMap<String, Integer> initMap = new LinkedHashMap<String,Integer>();
            NodeList states = lifecycleElement.getElementsByTagName("state");
            for (int i = 0, idx = 0; i < states.getLength(); i++) {
                Element stateElement = (Element)states.item(i);
                String stateName = stateElement.getAttributes().getNamedItem("name").getNodeValue();
                
                initMap.put(stateName, idx);
                NodeList items = stateElement.getElementsByTagName("checkitem");
                if (items.getLength() == 0) {
                    initItems.add(new Item(idx++, stateName, "", false, 0));
                } else {
                    for (int j = 0; j < items.getLength(); j++) {
                        String itemName = items.item(j).getFirstChild().getNodeValue();
                        initItems.add(new Item(idx++, stateName, itemName, false, j));
                    }
                }
            }
            
            __baseLifeCycle = new RSLifeCycle(initItems.get(0)._status, initItems, initMap);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    private RSLifeCycle(String state, List<Item> items, LinkedHashMap<String,Integer> map) {
        _state = state;
        _items = items;
        _map = map;
    }
    
    public String getName() {
        return __name;
    }
    
    public String getState() {
        return _state;
    }
    
    public List<String> getValidStates() {
        return new ArrayList<String>(_map.keySet());
    }
    
    public Item getItem(int idx)
    {
        return _items.get(idx);
    }

    public void put(Resource asset) {
        if (asset != null) {
            asset.addAspect(__name);
            asset.setProperty("registry.LC.name", __name);
            asset.setProperty(__stateProp, _state);
    
            for (Item item : _items) {
                putItem(asset, item);
            }
        }
    }
    
    public static String getState(Resource asset) {
        return asset.getProperty(__stateProp);
    }
    
    public static void init(Resource asset) {
        __baseLifeCycle.put(asset);
    }
        
    public static boolean submit(Resource asset, String comment) {
        RSLifeCycle lc = get(asset);

        Item item = lc.getLastItem(false, false);

        if (item != null && item._name != null) {
            item._value = true;
            putItem(asset, item);
            if (comment != null && !comment.isEmpty()) {
                asset.setProperty(String.format(__commentPropFormat, item._index), comment);
            }
        }
        
        return (item != null);
    }
    
    public static boolean approve(Resource asset, String comment) {
        RSLifeCycle lc = get(asset);
        
        Item item = lc.getLastItem(true, true);
        
        if (item != null) {
            lc._state = item._status;
            lc.put(asset);
            asset.setProperty(String.format(__commentPropFormat, item._index), comment);
        }
  
        return (item != null);
    }
    
    public static boolean reject(Resource asset, String comment) {
        RSLifeCycle lc = get(asset);
        
        Item item = lc.getLastItem(true, false);
        
        if (item != null) {
            item._value = false;
            lc.put(asset);
            asset.setProperty(String.format(__commentPropFormat, item._index), comment);
        }
        
        return (item != null);
    }

    public static RSLifeCycle get(Resource asset) {
        String state;
        List<Item> items;
        LinkedHashMap<String, Integer> map;
        
        if (asset != null) {
            state = asset.getProperty(__stateProp);
            items = new ArrayList<Item>();
            map = new LinkedHashMap<String,Integer>();
            for (Entry<Object, Object> entry : asset.getProperties().entrySet()) {
                String propName = entry.getKey().toString();
                if (propName.startsWith("registry.custom_lifecycle.checklist.option")) {
                    int currIdx = Integer.parseInt(propName.substring(42, 
                                                propName.lastIndexOf('.')));
    
                    int currOrder = -1;
                    boolean currValue = false;
                    String currName = "";
                    String currStatus = "";
    
                    @SuppressWarnings("unchecked")
					List<String> subitems = (List<String>)entry.getValue();
                    for (String subitem : subitems)
                    {
                        if (subitem.startsWith("order:")) {
                            currOrder = Integer.parseInt(subitem.substring(6));
                        }
                        if (subitem.startsWith("value:")) {
                            currValue = Boolean.parseBoolean(subitem.substring(6));
                        }
                        if (subitem.startsWith("name:")) {
                            currName = subitem.substring(5);
                        }
                        if (subitem.startsWith("status:")) {
                            currStatus = subitem.substring(7);
                        }
                    }
                    
                    int idx = -1;
                    for (int i=0; i < items.size(); i++) 
                    {
                        if(currIdx < items.get(i)._index) {
                            idx = i;
                            break;
                        }
                    }
                    
                    Item item = new Item(currIdx, currStatus, currName, currValue, currOrder);
                    if (idx < 0) {
                        items.add(item);
                    } else {
                        items.add(idx, item);
                    }
                    
                    if (currOrder == 0) {
                        map.put(currStatus, currIdx);
                    }
                }
            }
        } else {
            state = __baseLifeCycle._state;
            items = new ArrayList<Item>(__baseLifeCycle._items);
            map = new LinkedHashMap<String, Integer>(__baseLifeCycle._map);
        }
            
        return new RSLifeCycle(state, items, map);
    }
    
    /**
     * Returns the last item in sequence for the current status, using
     * following rules, where x is the first item from the next section:
     * <pre>
     * 012345 <- position -+ 
     *                     V 
     * FFFFFx ? F,*  ->   [0]
     * TTTFFx ? F,*  ->   [3]
     * TTTTTx ? F,*  ->  null
     * FFFFFx ? T,*  ->  null
     * TTTFFx ? T,*  ->  null
     * TTTTTx ? T,F  ->   [4]
     * TTTTTx ? T,T  ->   [5]
     * </pre>
     * @param value specifies desired value of the item should have
     * @param next specifies whether the following item should 
     *        be returned, will be ignored if value == false
     * @return
     */
    private Item getLastItem(boolean value, boolean next) {
        Item item = null;
        
        Integer index = _map.get(_state);
        if (index != null) {
            Item last = null;
            Item curr = null;
            int idx = index.intValue();
            int size = _items.size();
            while (idx >= 0 && idx < size) {
                if ((curr = _items.get(idx++)) != null) {
                    if (_state.equals(curr._status)) {
                        if (curr._value) {
                            last = curr;
                            continue;
                        } else {
                            if (!value) {
                                item = curr;
                            }
                            break;
                        }
                    } else {
                        if (last != null) {
                            if (next) {
                                item = curr;
                            } else {
                                item = last;
                            }
                        }
                        break;
                    }
                }
            }
        }
        
        return item;
    }
    
    private static void putItem(Resource asset, final Item item) {
        if (asset != null && item != null) {
            asset.setProperty(String.format(__optionPropFormat, item._index),
                              new ArrayList<String>() {
                                  /**
								 * 
								 */
								private static final long serialVersionUID = -510451693337655141L;

								{
                                      add("status:" + item._status);
                                      if (!item._name.isEmpty()) {
                                          add("name:"   + item._name);
                                          add("value:"  + item._value);
                                          add("order:"  + item._order);
                                      }
                                  }
                              });
        }
    }
    
    public static class Item {
        Item(int index, String status, String name, boolean value, int order) {
            _index = index;
            _status = status;
            _name = name;
            _value = value;
            _order = order;
        }

        private int _index;
        private String _status;
        private String _name;
        private boolean _value;
        private int _order;

        public String getStatus() {
            return _status;
        }

        public String getName() {
            return _name;
        }

        public boolean isValue() {
            return _value;
        }

        public int getOrder() {
            return _order;
        }

        @Override
        public String toString() {
            return _index + "/" + _order + ":" + _status + "/" + _name;
        }
    }
}
