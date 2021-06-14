/*
 * This data type defines the interface of a remote object of type RegisterRemoteObject.
 *  
 */
package DestinationAirport.Interfaces;



import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jgabrantes
 */
public interface RegisterInterface extends Remote{
    
  /**
   *  Binds a remote reference to the specified name in this registry.
   *
   *    @param name the name to associate with the remote reference
   *    @param ref reference to the remote object
   *
   */
public void bind (String name, Remote ref) throws RemoteException, AlreadyBoundException;

 /**
   *  Removes the binding for the specified name in this registry.
   *
   *    @param name the name associated with the remote reference
   *
   */

   public void unbind (String name) throws RemoteException, NotBoundException;

  /**
   *  Replaces the binding for the specified name in this registry with the supplied remote reference.
   *  If a previous binding for the specified name exists, it is discarded.
   *
   *    @param name the name to associate with the remote reference
   *    @param ref reference to the remote object
   */

   public void rebind (String name, Remote ref) throws RemoteException;
}
