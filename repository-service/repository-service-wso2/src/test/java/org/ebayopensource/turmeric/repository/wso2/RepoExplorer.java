/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

package org.ebayopensource.turmeric.repository.wso2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Map;

import org.wso2.carbon.registry.app.RemoteRegistry;
import org.wso2.carbon.registry.core.Association;
import org.wso2.carbon.registry.core.Collection;
import org.wso2.carbon.registry.core.Resource;

public class RepoExplorer extends Wso2Base {
   static RemoteRegistry registry;

   public static void main(String[] commandline) throws Exception {

      String user = "admin";
      String url = "https://localhost:9443/registry";
      registry = new RemoteRegistry(new URL(url), user, "admin");

      BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
      String line;
      String cwd = "/_system/";

      System.out.print("\n" + user + "@" + url + cwd + "\n> ");
      System.out.flush();
      while ((line = in.readLine()) != null) {
         String[] args = line.split(" ");
         String cmd = args[0];
         String path = (args.length > 1) ? args[1] : cwd;

         if (".".equals(path)) {
            path = cwd;
         } else if ("..".equals(path)) {
            path = cwd.substring(0, cwd.lastIndexOf("/"));
            path = path.substring(0, path.lastIndexOf("/"));
            if (path.length() == 0) {
               path = "/";
            }
         }

         if (!path.startsWith("/")) {
            path = cwd + path;
         }

         try {
            if ("help".equals(cmd)) {
               help();
            } else if (!registry.resourceExists(path)) {
               System.out.println("!EXISTS " + path);
            } else {
               Resource resource = registry.get(path);

               if ("x".equals(cmd)) {
                  if (resource instanceof Collection) {
                     cwd = resource.getPath();
                  } else {
                     cwd = resource.getParentPath();
                  }

                  if (!cwd.endsWith("/")) {
                     cwd += "/";
                  }

                  resource = registry.get(cwd);
                  System.out.println(resource.getPath());

                  Collection collection = (Collection) resource;
                  String[] children = collection.getChildren();

                  // drill down
                  while (children != null && children.length == 1) {
                     Resource child = registry.get(children[0]);
                     if (child instanceof Collection) {
                        resource = collection = (Collection) child;
                        children = collection.getChildren();
                        System.out.println(resource.getPath());

                        cwd = resource.getPath();
                        if (!cwd.endsWith("/")) {
                           cwd += "/";
                        }
                     } else {
                        break;
                     }
                  }

                  if (children != null) {
                     System.out.println("--");
                     for (String child : children) {
                        System.out.println(child.substring(cwd.length()));
                     }
                  }
               } else if ("ls".equals(cmd)) {
                  if (resource instanceof Collection) {
                     Collection collection = (Collection) resource;
                     String[] children = collection.getChildren();
                     if (children != null) {
                        for (String child : children) {
                           System.out.println(child.substring(cwd.length()));
                        }
                     }
                  }
               }

               else if ("info".equals(cmd)) {
                  System.out.println(resource.getPath());
                  System.out.println("id=" + resource.getId());
                  System.out.println("permanentPath=" + resource.getPermanentPath());
                  System.out.println("description=" + resource.getDescription());
                  System.out.println("properties=");
                  dump(resource.getProperties(), "");
                  System.out.println();
                  System.out.println("mediatype=" + resource.getMediaType());
                  System.out.println("state=" + resource.getState());
                  System.out.println("aspects=" + resource.getAspects());
                  System.out.println("content=" + (resource.getContent() != null));
                  System.out.println("created=" + resource.getCreatedTime());
                  System.out.println("lastUser=" + resource.getLastUpdaterUserName());
                  System.out.println("lastUpdated=" + resource.getLastModified());

                  if (path.indexOf(";version:") < 0) {
                     System.out.println("versions=");
                     for (String version : registry.getVersions(path)) {
                        System.out.println(version);
                     }
                     System.out.println();
                  }
                  Association[] assoc = registry.getAllAssociations(resource.getPath());
                  if (assoc != null) {
                     for (Association a : assoc) {
                        System.out.println(a.getSourcePath() + " " + a.getAssociationType() + " "
                                 + a.getDestinationPath());
                     }
                  }

               } else if ("get".equals(cmd)) {
                  System.out.println("--");
                  try {
                     dump(resource.getContentStream(), "");
                  } catch (Exception e) {
                     dump(resource.getContent(), "");
                  }
                  System.out.println("\n==");
               } else if ("cd".equals(cmd)) {
                  if (resource instanceof Collection) {
                     cwd = resource.getPath();
                  } else {
                     cwd = resource.getParentPath();
                  }

                  if (!cwd.endsWith("/")) {
                     cwd += "/";
                  }
               } else if ("tree".equals(cmd)) {
                  System.out.println(resource.getPath());
                  if (resource instanceof Collection) {
                     tree((Collection) resource, "");
                  }
               } else if ("find".equals(cmd)) {
                  if (resource instanceof Collection) {
                     find((Collection) resource);
                  }
               } else if ("rm".equals(cmd)) {
                  System.out.println("RM " + resource.getPath());
                  Association[] associations = registry.getAllAssociations(path);
                  if (associations != null) {
                     for (Association assoc : associations) {
                        String srcPath = assoc.getSourcePath();
                        String type = assoc.getAssociationType();
                        if (path.equals(srcPath)) {
                           String dstPath = assoc.getDestinationPath();
                           registry.removeAssociation(srcPath, dstPath, type);
                        }
                     }
                  }
                  registry.delete(path);
               }
            }

         } catch (Exception e) {
            e.printStackTrace();
            System.err.flush();
         }

         System.out.print("\n" + user + "@" + url + cwd + "\n> ");
         System.out.flush();
      }

   }

   private static void dump(Object content, String indent) throws Exception {
      if (content == null) {
         System.out.print("null");
      } else {
         if (content instanceof byte[]) {
            System.out.print(new String((byte[]) content, "UTF-8"));
         } else if (content instanceof Map<?, ?>) {
            System.out.print("{\n");
            @SuppressWarnings("unchecked")
            Map<Object, Object> map = (Map<Object, Object>) content;
            map.entrySet();
            for (Map.Entry<Object, Object> e : map.entrySet()) {
               System.out.print(indent + "  " + e.getKey() + '=');
               dump(e.getValue(), indent + "  ");
               System.out.print(",\n");
            }
            System.out.print(indent + "}");
         } else if (content.getClass().isArray()) {
            System.out.print("[\n");
            int len = Array.getLength(content);
            for (int i = 0; i < len; i++) {
               if (i > 0) {
                  System.out.print(",\n");
               }
               System.out.print(indent);
               dump(Array.get(content, i), indent + "  ");
            }
            System.out.print(indent + "]");
         } else if (content instanceof java.util.Collection) {
            @SuppressWarnings("unchecked")
            java.util.Collection<Object> c = (java.util.Collection<Object>) content;
            System.out.print("[\n");
            for (Object o : c) {
               System.out.print(indent + "  ");
               dump(o, indent + "  ");
               System.out.print(",\n");
            }
            System.out.print(indent + "]");
         } else if (content instanceof InputStream) {
            InputStream cin = (InputStream) content;
            byte[] buf = new byte[4096];
            int len = cin.read(buf);
            StringBuilder b = new StringBuilder();
            while (len > 0) {
               b.append(new String(buf, 0, len, "UTF-8"));
               len = cin.read(buf);
            }
            dump(b.toString(), indent);
         } else if (content instanceof String && content.toString().startsWith("<")
                  && content.toString().indexOf('\n') < 0) {
            dumpXml(content.toString(), indent);
         } else {
            System.out.print(content);
         }
      }
   }

   private static void dumpXml(String string, String indent) {
      int content = -1;
      int tag = -1;
      int state = 0;

      for (int i = 0; i < string.length(); i++) {
         char c = string.charAt(i);

         switch (state) {
            case 0: // looking for tag
            {
               if (c == '<') {
                  if (content >= 0) {
                     System.out.println(indent + string.substring(content, i));
                  }
                  tag = i;
                  content = -1;
                  state = 1;
               } else if (content < 0) {
                  content = i;
               }
            }
               break;

            case 1: // start of tag
            {
               if (c == '/') {
                  state = 3;
               } else {
                  state = 2;
               }
            }
               break;

            case 2: // in opening tag
            {
               if (c == '>') {
                  System.out.println(indent + string.substring(tag, i + 1));
                  indent += "  ";
                  tag = -1;
                  state = 0;
               } else if (c == '/') {
                  state = 4;
               } else if (c == '"') {
                  state = 5;
               }
            }
               break;

            case 3: // in closing tag
            {
               if (c == '>') {
                  indent = indent.substring(0, indent.length() - 2);
                  System.out.println(indent + string.substring(tag, i + 1));
                  tag = -1;
                  state = 0;
               }
            }
               break;

            case 4: // in open and close tag
            {
               if (c == '>') {
                  System.out.println(indent + string.substring(tag, i + 1));
                  tag = -1;
                  state = 0;
               } else {
                  state = 2;
               }
            }
               break;

            case 5: // in quote in open tag
            {
               if (c == '"') {
                  state = 2;
               }
            }
               break;

         }

      }
   }

   private static void help() {
      System.out.println("help");
      System.out.println("x <abspath|relpath>");
      System.out.println("ls [abspath|relpath]");
      System.out.println("cd <abspath|relpath>");
      System.out.println("info <abspath|relpath>");
      System.out.println("get <abspath|relpath>");
      System.out.println("tree <abspath|relpath>");
      System.out.println("find <abspath|relpath>");
   }

   private static void tree(Collection collection, String indent) throws Exception {
      String[] children = collection.getChildren();
      if (children != null) {
         for (int i = 0; i < children.length; i++) {
            String child = children[i];
            String name = child.substring(collection.getPath().length());
            if (name.startsWith("/")) {
               name = name.substring(1);
            }

            Resource resource = registry.get(child);

            if (resource instanceof Collection) {
               System.out.println(indent + " +-" + name + "/");
               tree((Collection) resource, indent + ((i + 1 == children.length) ? "   " : " | "));
            } else {
               System.out.println(indent + " +-" + name);
            }

         }
      }
   }

   private static void find(Collection collection) throws Exception {
      String[] children = collection.getChildren();
      if (children != null) {
         for (int i = 0; i < children.length; i++) {
            String child = children[i];
            Resource resource = registry.get(child);

            if (resource instanceof Collection) {
               find((Collection) resource);
            } else {
               System.out.println(resource.getPath());
            }

         }
      }
   }

}
