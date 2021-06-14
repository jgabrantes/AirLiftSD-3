/*
 * This class instantiates and registers a remote object that enables the registration of other remote objects
 *  located in the same or other processing nodes in the local registry service.
 */
package Registry.MainProgram;

import Registry.Interfaces.RegisterInterface;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author jgabrantes
 */
class ServerRegisterRemoteObject {
    
    /**
     * Used to check if the service must  be terminated.
     */
    static boolean serviceEnd = false;
    
    /**
     * Main class
     * @param args 
     */
    public static void main(String[] args){
        
        String regHostName = Parameters.REGISTRY_HOST_NAME;
        int regPortNum = Parameters.REGISTRY_PORT;
        
        /* create and install the security manager */

        if (System.getSecurityManager () == null)
        System.setSecurityManager (new SecurityManager ());

        /* instantiate a registration remote object and generate a stub for it */
         RegisterRemoteObject regEngine = new RegisterRemoteObject (regHostName, regPortNum, 6);
         RegisterInterface registerInt = null;
         int listeningPort = Parameters.SERVER_REGISTRY_PORT;

         try
         { registerInt = (RegisterInterface) UnicastRemoteObject.exportObject(regEngine, listeningPort);
         }
         catch (RemoteException e)
         { System.out.println("RegisterRemoteObject stub generation exception: " + e.getMessage ());
           System.exit (1);
         }

        /* register it with the local registry service */
         String nameEntry = Parameters.REGISTER_HANDLER;
         Registry registry = null;

         try
         { registry = LocateRegistry.getRegistry (regHostName, regPortNum);
         }
         catch (RemoteException e)
         { System.out.println("RMI registry creation exception: " + e.getMessage ());
           System.exit (1);
         }

         try
         { registry.rebind (nameEntry, registerInt);
         }
         catch (RemoteException e)
         { System.out.println("RegisterRemoteObject remote exception on registration: " + e.getMessage ());
           System.exit (1);
         }
         System.out.println("RegisterRemoteObject object was registered!");

         /* Wait for the service to end */
            while(!serviceEnd){
                try {
                    synchronized(regEngine){
                        regEngine.wait();
                    }
                } catch (InterruptedException ex) {
                    System.out.println("Main thread of registry was interrupted.");
                    System.exit(1);
                }
            }

            System.out.println("Registry finished execution.");

            /* Unregister shared region */
            try
            { registry.unbind(nameEntry);
            }catch (RemoteException e)
            { System.out.println ("RegisterRemoteObject unregistration exception: " + e.getMessage ());
              e.printStackTrace ();
              System.exit (1);
            } catch (NotBoundException ex) {
              System.out.println ("RegisterRemoteObject unregistration exception: " + ex.getMessage ());
              ex.printStackTrace ();
              System.exit (1);
            }
            System.out.println ("RegisterRemoteObject object was unregistered!");

            /* Unexport shared region */
            try
            { UnicastRemoteObject.unexportObject (regEngine, false);
            }
            catch (RemoteException e)
            { System.out.println ("RegisterRemoteObject unexport exception: " + e.getMessage ());
              e.printStackTrace ();
              System.exit (1);
            }

            System.out.println ("RegisterRemoteObject object was unexported successfully!");
        
        
        
    }
    
    
}
