/*
 *  This data type defines a generic functionality to register remote objects in the local registry service.
 *  Communication is based in Java RMI.
 */
package Registry.MainProgram;

import Registry.Interfaces.RegisterInterface;
import java.nio.channels.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author jgabrantes
 */
public class RegisterRemoteObject implements RegisterInterface {
    
    /**
     * Local host name
     *     @serialField regHostName
     */
    private String regHostName;
    
    /**
     * Local registry port number
     * @SerialField regPortNum;
     */
    private int regPortNum;
    
    /**
    * Number of unbinds allowed in registered. After this number of unbinds, service shuts down.
    */
    private int numUnbinds = 4;
    
    /**
     * Instantiation of a register object
     * @param regHostName
     * @param regPortNum
     * @param numUnbinds
     */
    public RegisterRemoteObject(String regHostName, int regPortNum, int numUnbinds){
        if(regHostName == null)
            throw new NullPointerException ("RegisterRemoteObject: null pointer parameter on instantiation!");
        this.regHostName = regHostName;
        
        if ((regPortNum >= 4000) && (regPortNum <= 65535))
			this.regPortNum = regPortNum;
	
        if(numUnbinds >0) this.numUnbinds = numUnbinds;
    }
    
    /**
   *  Binds a remote reference to the specified name in this registry.
   *
   *    @param name the name to associate with the remote reference
   *    @param ref reference to the remote object
   */

   @Override
   public void bind (String name, Remote ref) throws RemoteException, AlreadyBoundException, java.rmi.AlreadyBoundException
   {
     Registry registry;

     if ((name == null) || (ref == null))
        throw new NullPointerException ("RegisterRemoteObject: null pointer parameter(s) on on bind!");
     registry = LocateRegistry.getRegistry (regHostName, regPortNum);
     registry.bind (name, ref);
   }

 /**
   *  Removes the binding for the specified name in this registry.
   *
   *    @param name the name associated with the remote reference
   *
   */

   @Override
   public synchronized void unbind (String name) throws RemoteException, NotBoundException
   {
     Registry registry;

     if ((name == null))
        throw new NullPointerException ("RegisterRemoteObject: null pointer parameter(s) on unbind!");
     registry = LocateRegistry.getRegistry (regHostName, regPortNum);
     registry.unbind (name);
     
     numUnbinds--;
     if(numUnbinds<=0){
        ServerRegisterRemoteObject.serviceEnd = true;
        notifyAll();
     }
   }

  /**
   *  Replaces the binding for the specified name in this registry with the supplied remote reference.
   *  If a previous binding for the specified name exists, it is discarded.
   *
   *    @param name the name to associate with the remote reference
   *    @param ref reference to the remote object
   *
   */

   @Override
   public void rebind (String name, Remote ref) throws RemoteException
   {
     Registry registry;

     if ((name == null) || (ref == null))
        throw new NullPointerException ("RegisterRemoteObject: null pointer parameter(s) on rebind!");
     registry = LocateRegistry.getRegistry (regHostName, regPortNum);
     registry.rebind (name, ref);
   }
}
