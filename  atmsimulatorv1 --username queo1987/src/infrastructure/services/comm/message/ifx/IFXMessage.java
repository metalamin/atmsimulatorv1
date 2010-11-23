/*
 * Created on 10/06/2005
 *
 */
package infrastructure.services.comm.message.ifx;
import java.io.ByteArrayInputStream;
import org.sourceforge.ifx.framework.element.IFX;
import org.sourceforge.ifx.utils.IFXDocumentHandler;
import org.sourceforge.ifx.basetypes.*;
import java.lang.reflect.*;
import infrastructure.services.comm.message.Message;
import java.io.Serializable;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import org.jdom.*;
import java.util.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.jdom.transform.*;
/**
 * @author Arya Baher
 *
 */
public class IFXMessage implements Message, Serializable{
	
	IFX ifx;
	static final String PATH_DELIMITER = ".";
	static final String GETTER_PREFIX = "get";
	static final String SETTER_PREFIX = "set";        
	static final String LEFT_BRACKET = "[";
	static final String RIGHT_BRACKET = "]";
        static final String IFX_URI = "http://sourceforge.net/ifx-framework/ifx";
	/**
	 * 
	 */
	public IFXMessage() {
		super();
                ifx = new IFX();
	}

	public IFXMessage(String xmlString) {
                super();
                try{
                    //<IFX xmlns="http://sourceforge.net/ifx-framework/ifx" xmlns:="http://sourceforge.net/ifx-framework/ifx">
                    xmlString = xmlString.replace("xmlns:=\"http://sourceforge.net/ifx-framework/ifx\"", "");
                    Map props = new HashMap();
                    props.put(IFX_URI,"cfg/ifx/1.7/IFX170_extended.xsd");
                    ByteArrayInputStream bais = new ByteArrayInputStream(xmlString.getBytes());
                    Document docIn = IFXDocumentHandler.read(bais, true, props);
                    ifx = (IFX) IFXDocumentHandler.parse(docIn);
		}
		catch (Exception e){
			e.printStackTrace();
		}                
	}

	/**
	 *  Crea un objeto IFX a partir de un inputStream que puede ser un FileInputStream
	 * @param istream - InputStream desde donde se lee el mensaje ifx
	 */
        public IFXMessage(InputStream istream) {
		super();
                try{
                    Map props = new HashMap();
                    props.put(IFX_URI,"cfg/IFX150.xsd");                
                    Document docIn = IFXDocumentHandler.read(istream, true, props);
                    ifx = (IFX) IFXDocumentHandler.parse(docIn);
		}
		catch (Exception e){
			e.printStackTrace();
		}                
	}

	/**
	 *  Persiste el objeto en formato xml en el OutputStream especificado
	 * @param ostream - OutputStream donde se persiste el mensaje
	 */
        public void write(OutputStream ostream){
            try{
                Document docOut = IFXDocumentHandler.build(ifx, null, IFX_URI);
                IFXDocumentHandler.write(docOut,0,null,ostream);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

	/**
	 *  Devuelve el mensaje en formato xml en un objeto String
	 */
    @Override
        public String toString(){
            String result = null;
            try{
                Document docOut = IFXDocumentHandler.build(ifx, null, IFX_URI);

                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                StreamResult sresult = new StreamResult(new StringWriter());
                JDOMSource source = new JDOMSource(docOut);
                transformer.transform(source, sresult);
                String xmlString = sresult.getWriter().toString();
                result = xmlString;
                //IFXDocumentHandler.write(docOut,0,null,ostream);
            } catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

	/**
	 *  Devuelve el valor del objeto asociado a la clave especificada por parámetro
	 * @param key 	- Clave del objeto en el mensaje
	 * @return
	 */
	public Object getElement(Object key){
            Object result=null;
            try{
                result = ((IBaseType)getInternalElement(key)).getString();
            }catch(Exception e){
            }
            return result;
	}
	
	/**
	 * @param key 	- Clave única del objeto en el mensaje
	 * @param value - Valor a setear en el objeto
	 */
	public void setElement(Object key, Object value){
            if (getInternalElement(key)!=null){
                ((IBaseType)getInternalElement(key)).setString(value.toString());
            }
	}
	
	
	/**
	 * Devuelve el objeto asociado a la clave especificada por parámetro
	 * @param key - Clave única del objeto en el mensaje
	 * @return
	 */
        
        private Object getInternalElement(Object key){
            String objectPath = (String) key;
            String objectName = null;
            Object ifxElement = null;

                // el primer elemento del documento es la raiz
                ifxElement = ifx;
                int dotPosition = -1;
                dotPosition = objectPath.indexOf(PATH_DELIMITER);
                while (dotPosition != -1){
                    
                    // parametros para la invocacion
                    objectName = objectPath.substring(0, dotPosition);
                    
                    ifxElement = getChildElement(ifxElement, objectName);
                    
                    // variables para el ciclo
                    objectPath = objectPath.substring(dotPosition+1, objectPath.length());
                    dotPosition = objectPath.indexOf(PATH_DELIMITER);
                }
                objectName = objectPath;
                ifxElement = getChildElement(ifxElement, objectName);
                

            return (IBaseType)ifxElement;
        }
            
        private Object getChildElement(Object father, String childName){
            Object child = null;
            int arrayIndex = 0;
            String methodName = null;
            String setMethodName = null;
            Method method;
            Method setMethod;
            Class[] parameterTypes = new Class[] {};
            Class[] setParameterTypes = new Class[] {};
            Object[] arguments = new Object[] {};
            Object[] setParameters = new Object[] {};
            Object object = null;
            
            try{
                // Obtiene una referencia a la clase del padre
                Class c = father.getClass();
    
                // Si el hijo es un elemento de un array obtiene el indice del mismo
                if (childName.indexOf(LEFT_BRACKET) != -1){
                    arrayIndex = (new Integer(
                                              childName.substring(childName.indexOf(LEFT_BRACKET)+1,
                                                                childName.indexOf(RIGHT_BRACKET))
                                              )
                                  ).intValue();
                    childName = childName.substring(0,childName.indexOf(LEFT_BRACKET));
                }
    
                // Arma el nombre del metodo que obtiene el elemento hijo
                methodName = GETTER_PREFIX.concat(childName);
                
                // Obtiene una referencia al metodo getter
                method = c.getMethod(methodName, parameterTypes);

                
                // Si el metodo retorna null significa que el elemento hijo no existe aun
                // por lo que si la flag asi lo indica crea el elemento hijo
                if (method.invoke(father, arguments)==null){
                    try{
                        // Obtiene la clase del elemento hijo
                        String classPath = method.getReturnType().getCanonicalName();
                        if (method.getReturnType().isArray()){
                            classPath = classPath.substring(0,classPath.indexOf(LEFT_BRACKET));
                        }
                        // Crea un objeto de dicha clase,
                        // y si es un array primero crea el array y luego setea el objeto en el indice del elemento hijo
                        Class classDefinition = Class.forName(classPath);
                        if (method.getReturnType().isArray()){
                            object = Array.newInstance(classDefinition, arrayIndex + 1);
                            Array.set(object, 0, classDefinition.newInstance());
                        } else{
                            object = classDefinition.newInstance();
                        }
                        // Invoca el metodo para setear el elemento hijo al elemento padre
                        setParameters = new Object[] {object};
                        setParameterTypes = new Class[] {method.getReturnType()};
                        setMethodName = SETTER_PREFIX.concat(childName);
                        setMethod = c.getMethod(setMethodName, setParameterTypes);
                        setMethod.invoke(father, setParameters);
                        
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
                
                // Si el metodo retorna un array, hay que devolver el elemento que está en la posicion arrayIndex
                if (method.getReturnType().isArray()){
                    Object array=null;
                    Object auxArray=null;
                    array=method.invoke(father, arguments);
                    try{
                        // Si el arrayIndex supera el tamaño del array
                        // hay que agrandar el array y crear un objeto nuevo en la posicion arrayIndex
                        if (Array.getLength(array) < arrayIndex + 1){
                            // crea un array auxiliar y copia el array original a este
                            auxArray = Array.newInstance(method.getReturnType().getComponentType(),Array.getLength(array));
                            System.arraycopy(array, 0, auxArray, 0, Array.getLength(array));
                            // agranda el largo del array original
                            array = Array.newInstance(method.getReturnType().getComponentType(),arrayIndex + 1);
                            // reestablece los valores originales del array a partir del array auxiliar
                            System.arraycopy(auxArray, 0, array, 0, Array.getLength(auxArray));
                            // setea el nuevo objeto en la posicion arrayIndex del array original
                            Array.set(array, arrayIndex, method.getReturnType().getComponentType().newInstance());
                            // debido a quese creó una nueva instancia del array hay que setearlo nuevamente al padre
                        // Invoca el metodo para setear el elemento hijo al elemento padre
                            object = array;
                            setParameters = new Object[] {object};
                            setParameterTypes = new Class[] {method.getReturnType()};
                            setMethodName = SETTER_PREFIX.concat(childName);
                            setMethod = c.getMethod(setMethodName, setParameterTypes);
                            setMethod.invoke(father, setParameters);                            
                        } else{
                            // Si la posicion arrayIndex del array devuelve null
                            // crea el objeto y lo instancia en esa posicion del array
                            if (Array.get(array,arrayIndex)==null){
                                Array.set(array, arrayIndex, method.getReturnType().getComponentType().newInstance());
                            }
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    child = Array.get(array,arrayIndex);
                    
                } else{
                    // Si el metodo retorna directamente el elemento hijo entonces lo retorna
                    child = method.invoke(father, arguments);
                }
            } catch (NoSuchMethodException e) {
                child = null;
                System.out.println(e);
            } catch (IllegalAccessException e) {
                child = null;
                System.out.println(e);
            } catch (InvocationTargetException e) {
                child = null;
                System.out.println(e);
            }
            return child;
        }


        private Object getInternalElement2(Object key){
            String methodPath = (String) key;
            String methodName = null;
            String setMethodName = null;
            String objectName = null;
            Object ifxElement = null;
            int arrayIndex = 0;
            Method method;
            Method setMethod;
            Class[] parameterTypes = new Class[] {};
            Class[] setParameterTypes = new Class[] {};
            Object[] arguments = new Object[] {};
            Object[] setArguments = new Object[] {};
            try {
                // el primer elemento del documento es la raiz
                ifxElement = ifx;
                Class c = ifxElement.getClass();
                int dotPosition = -1;
                dotPosition = methodPath.indexOf(PATH_DELIMITER);
                while (dotPosition != -1){
                    objectName = methodPath.substring(0, dotPosition);
                    if (objectName.indexOf(LEFT_BRACKET) != -1){
                        arrayIndex = (new Integer(objectName.substring(objectName.indexOf(LEFT_BRACKET)+1,objectName.indexOf(RIGHT_BRACKET)))).intValue();
                        objectName = objectName.substring(0,objectName.indexOf(LEFT_BRACKET));
                    }
                    methodName = GETTER_PREFIX.concat(objectName);
                    methodPath = methodPath.substring(dotPosition+1, methodPath.length());
                    dotPosition = methodPath.indexOf(PATH_DELIMITER);
                    method = c.getMethod(methodName, parameterTypes);
                    if (method.invoke(ifxElement, arguments)==null){
                        try{
                            String classPath = method.getReturnType().getCanonicalName();
                            if (method.getReturnType().isArray()){
                                classPath = classPath.substring(0,classPath.indexOf(LEFT_BRACKET));
                            }
                            Class classDefinition = Class.forName(classPath);
                            Object object = null;
                            if (method.getReturnType().isArray()){
                                object = Array.newInstance(classDefinition, arrayIndex + 1);
                                Array.set(object, 0, classDefinition.newInstance());
                            } else{
                                object = classDefinition.newInstance();
                            }
                            setArguments = new Object[] {object};
                            setParameterTypes = new Class[] {method.getReturnType()};
                            setMethodName = SETTER_PREFIX.concat(objectName);
                            setMethod = c.getMethod(setMethodName, setParameterTypes);
                            setMethod.invoke(ifxElement, setArguments);
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    if (method.getReturnType().isArray()){
                        Object array=null;
                        Object auxArray=null;
                        array=method.invoke(ifxElement, arguments);
                        try{
                            auxArray = Array.newInstance(method.getReturnType().getComponentType(),Array.getLength(array));
                            System.arraycopy(array, 0, auxArray, 0, Array.getLength(array));
                            if (Array.getLength(array) < arrayIndex + 1){
                                array = Array.newInstance(method.getReturnType().getComponentType(),arrayIndex + 1);
                                System.arraycopy(auxArray, 0, array, 0, Array.getLength(auxArray));
                                Array.set(array, arrayIndex, method.getReturnType().getComponentType().newInstance());
                            } else{
                                if (Array.get(array,arrayIndex)==null){
                                    Array.set(array, arrayIndex, method.getReturnType().getComponentType().newInstance());
                                }
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                        ifxElement = Array.get(array,arrayIndex);
                    } else{
                        ifxElement = method.invoke(ifxElement, arguments);
                    }
                    c = ifxElement.getClass();
                }
                objectName = methodPath;
                if (objectName.indexOf(LEFT_BRACKET) != -1){
                    arrayIndex = (new Integer(objectName.substring(objectName.indexOf(LEFT_BRACKET)+1,objectName.indexOf(RIGHT_BRACKET)))).intValue();
                    objectName = objectName.substring(0,objectName.indexOf(LEFT_BRACKET));
                }
                methodName = GETTER_PREFIX.concat(objectName);
                methodPath = methodPath.substring(dotPosition+1, methodPath.length());
                dotPosition = methodPath.indexOf(PATH_DELIMITER);
                method = c.getMethod(methodName, parameterTypes);
                if (method.invoke(ifxElement, arguments)==null){
                    try{
                        String classPath = method.getReturnType().getCanonicalName();
                        if (method.getReturnType().isArray()){
                            classPath = classPath.substring(0,classPath.indexOf(LEFT_BRACKET));
                        }
                        Class classDefinition = Class.forName(classPath);
                        Object object = null;
                        if (method.getReturnType().isArray()){
                            object = Array.newInstance(classDefinition, arrayIndex + 1);
                            Array.set(object, 0, classDefinition.newInstance());
                        } else{
                            object = classDefinition.newInstance();
                        }
                        setArguments = new Object[] {object};
                        setParameterTypes = new Class[] {method.getReturnType()};
                        setMethodName = SETTER_PREFIX.concat(objectName);
                        setMethod = c.getMethod(setMethodName, setParameterTypes);
                        setMethod.invoke(ifxElement, setArguments);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
                if (method.getReturnType().isArray()){
                    ifxElement = Array.get(method.invoke(ifxElement, arguments),arrayIndex);
                } else{
                    ifxElement = method.invoke(ifxElement, arguments);
                }
            } catch (NoSuchMethodException e) {
                ifxElement = null;
                System.out.println(e);
            } catch (IllegalAccessException e) {
                ifxElement = null;
                System.out.println(e);
            } catch (InvocationTargetException e) {
                ifxElement = null;
                System.out.println(e);
            }
            return (IBaseType)ifxElement;
        }

}
