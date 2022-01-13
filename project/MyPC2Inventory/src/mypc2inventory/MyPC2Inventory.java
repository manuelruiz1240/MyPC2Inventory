/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Ayuda en: https://github.com/profesorfalken/jHardware/blob/master/src/test/java/org/jutils/jhardware/HardwareInfoTest.java
 */ 
package mypc2inventory;

//Lista de Specs
//import java.io.BufferedReader;
import java.io.File;
//import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map.Entry;
//import java.util.Set;
//import java.net.SocketException;
//import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

import org.jutils.jhardware.HardwareInfo;
import org.jutils.jhardware.model.GraphicsCard;
import org.jutils.jhardware.model.GraphicsCardInfo;
import org.jutils.jhardware.model.ProcessorInfo;
import org.jutils.jhardware.model.BiosInfo;
import org.jutils.jhardware.model.MotherboardInfo;

//Lista de software
import com.sun.jna.platform.win32.Advapi32Util;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
//import static jdk.nashorn.internal.objects.NativeObject.keys;

//Se descarto la idea de usar MySQL y ahora se va a enviar JSON para mayor seguridad
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map.Entry;
import org.jutils.jhardware.model.Display;
import org.jutils.jhardware.model.DisplayInfo;

public class MyPC2Inventory {

    private static String element;  //para recorrer el array de software

    public static void main(String[] args) throws Exception {
        com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        System.out.println("\nScanning your PC for inventory processes, please wait at least 20 seconds... \n");
        System.out.println("============ HARDWARE ============");

        InetAddress ip = InetAddress.getLocalHost();
        System.out.println("Current host - PC name : " + ip.getHostName());
        String UserName = System.getProperty("user.name");  System.out.println("Current PC Username : "+UserName);
        System.out.println("Current IP address : " + ip.getHostAddress());
        System.out.print("Current MAC address : ");
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        byte[] mac = network.getHardwareAddress();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        System.out.println(sb.toString());

        String nameOS= System.getProperty("os.name");
        System.out.println("Operating system Name : "+ nameOS);
        String osType= System.getProperty("os.arch");
        System.out.println("Operating system type : "+ osType);
        
        ProcessorInfo infoCPU = HardwareInfo.getProcessorInfo();
        System.out.println("CPU : " + infoCPU.getModelName() + " ("+ System.getenv("NUMBER_OF_PROCESSORS") +" cores)");
//        System.out.println("Testing getGraphicsCardsInfo...");
//        System.out.println("====================================");
        GraphicsCardInfo ginfo = HardwareInfo.getGraphicsCardInfo();

        //assertTrue(info != null && info.getGraphicsCards() != null);

        List<GraphicsCard> graphicsCards = ginfo.getGraphicsCards();

        String _gpu_1 = null, _gpu_2 = null;
        ArrayList gpu_list = new ArrayList(); //{};
        int gpu_index;
        gpu_index = 0;
        for (final GraphicsCard graphicsCard : graphicsCards) {
//            System.out.println("----------Graphics Card: " + graphicsCard.getName() + "---------");
            System.out.println("GPU: " + graphicsCard.getName());
                    
//            System.out.println("Manufacturer: " + graphicsCard.getManufacturer());
//            System.out.println("Device Type: " + graphicsCard.getDeviceType());
//            System.out.println("DAC: " + graphicsCard.getDacType());
//            System.out.println("Chip type: " + graphicsCard.getChipType());
//           System.out.println("Temperature: " + graphicsCard.getTemperature());
//            System.out.println("Fan Speed: " + graphicsCard.getFanSpeed());
//            System.out.println("------------------------------------------------------");
              gpu_list.add(graphicsCard.getName());              
              gpu_index++;
        }

for(int g=0; g < gpu_list.size(); g++){ //Preparando y limpiando datos para enviar JSON
    if(g==0){
        _gpu_1 = (String) gpu_list.get(g);
        _gpu_1 = _gpu_1.replaceAll(" ", "%20");
    }
    if(g==1){
        _gpu_2 = (String) gpu_list.get(g);
        _gpu_2 = _gpu_2.replaceAll(" ", "%20");
    }

}        




/*for(final GraphicsCard graphicsCard : graphicsCards){
    System.out.println(GraphicsCard[].graphicsCard.getName());
}*/
        
//        System.out.println("End testing getGraphicsCardsInfo...");
//        System.out.println("====================================");
        
        /* Get a list of all filesystem roots on this system */
        File[] roots = File.listRoots();
        
        /* For each filesystem root, print some info */
/*
        for (File root : roots) {
            Storage_1 = (int) (root.getTotalSpace()/1073741824);
            Storage_2 = (int) (root.getTotalSpace()/1073741824);
            Storage_3 = (int) (root.getTotalSpace()/1073741824);
            System.out.println("Almacenamiento (GB): " + Storage);
        } */

long Storage_1 = 0, Storage_2 = 0, Storage_3 = 0;
for (int r = 0; r < roots.length; r++) {
    System.out.println("Root - " + roots[r] + " (" + roots[r].getTotalSpace()/1073741824+" GB)");
    if(r==0){
        Storage_1 = (long) roots[0].getTotalSpace()/1073741824;
    }
    if(r==1){
        Storage_2 = (long) roots[1].getTotalSpace()/1073741824;
    }
    if(r==2){
        Storage_3 = (long) roots[2].getTotalSpace()/1073741824;
    }
}

        long physicalMemorySize = (long) Math.ceil(os.getTotalPhysicalMemorySize()/1073741824);
        long freePhysicalMemory = (long) Math.ceil(os.getFreePhysicalMemorySize()/1073741824);
        System.out.println("RAM total (GB) : \t" + physicalMemorySize);
        System.out.println("RAM disponible (GB): \t" + freePhysicalMemory);
        
        long freeSwapSize = (long) Math.ceil(os.getFreeSwapSpaceSize()/1073741824);
        System.out.println("freeSwapSize (GB) : \t" + freeSwapSize);

        BiosInfo BIOSinfo = HardwareInfo.getBiosInfo();
        
        System.out.println("BIOS manufacturer: " + BIOSinfo.getManufacturer());
        System.out.println("BIOS Version: " + BIOSinfo.getVersion());
        System.out.println("BIOS Last update : " + BIOSinfo.getDate());
        
        System.out.println("\n");
        
        
        System.out.println("Testing getDisplayInfo...");
        System.out.println("====================================");
        DisplayInfo info = HardwareInfo.getDisplayInfo();

     //   assertTrue(info != null && info.getDisplayDevices() != null);

        List<Display> displays = info.getDisplayDevices();

        displays.stream().map((display) -> {
            System.out.println("----------Display: " + display.getName() + "---------");
            return display;
        }).map((display) -> {
            System.out.println("Current Resolution: " + display.getCurrentResolution());
            return display;
        }).map((display) -> {
            System.out.println("Current Refresh Rate: " + display.getRefreshRate());
            return display;
        }).map((display) -> {
            System.out.println("Available Resolutions: " + Arrays.toString(display.getSupportedResolutions()));
            return display;
        }).forEachOrdered((_item) -> {
            System.out.println("------------------------------------------------------");
        });

        System.out.println("End testing getDisplayInfo...");
        System.out.println("====================================");
        
        System.out.println("============ SOFTWARE ============");
        
        //Lista de software
        ArrayList<String> arr = new ArrayList();
        String [] keys = Advapi32Util.registryGetKeys(HKEY_LOCAL_MACHINE,"Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall");
        String temp;
        for (String key : keys){
            temp = "Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall" + "\\" +key;
            TreeMap<String, Object> tr = Advapi32Util.registryGetValues(HKEY_LOCAL_MACHINE,temp); 
            if(tr.isEmpty()){
                if(!key.contains("Update"))//all the instances of update are not actually installed applications
                {
                arr.add(key);
                }
            } else {
                if(tr.containsKey("DisplayName")){
                    String str = (String) tr.get("DisplayName");
                    if(!str.contains("Update")){
                        arr.add(str);
                    }
                }
            }
        }

        for (Iterator<String> it = arr.iterator(); it.hasNext();) {
            element = it.next();
            System.out.println(element);
        }

        //JSON to PHP
        System.out.println("\n===== Sending results to Apache Server ===== \n");
        try {
   
           String _os = nameOS;
           _os = _os.replaceAll(" ", "%20");
      
           String _cpu = infoCPU.getModelName() + " ("+ System.getenv("NUMBER_OF_PROCESSORS") +" cores)";
           _cpu = _cpu.replaceAll(" ", "%20");
           //_cpu = _cpu.replaceAll(" ", "%28");
           //_cpu = _cpu.replaceAll(" ", "%2D");

           //_gpu_1 = _gpu_1.replaceAll(" ", "%20");  //Por el momento solo retorna la GPU integrada, desspues hay que incluir la GPU dedicada en el caso de contar con una
           //_gpu_2 = _gpu_2.replaceAll(" ", "%20");  //Por el momento solo retorna la GPU integrada, desspues hay que incluir la GPU dedicada en el caso de contar con una
           //_gpu = _gpu.replaceAll(" ", "%28");
           //_gpu = _gpu.replaceAll(" ", "%2D");

           UserName = UserName.replaceAll(" ", "%20");
           
           String _bm, _bv, _bd;
           _bm = BIOSinfo.getManufacturer();
           _bm = _bm.replaceAll(" ", "%20");
           //_bm = _bm.replaceAll(" ", "%28");
           //_bm = _bm.replaceAll(" ", "%2D");
           //_bm = _bm.replaceAll(" ", "%2E");
           _bv = BIOSinfo.getVersion();
           _bv = _bv.replaceAll(" ", "%20");
           //_bv = _bv.replaceAll(" ", "%28");
           //_bv = _bv.replaceAll(" ", "%2D");
           //_bv = _bv.replaceAll(" ", "%2E");
           _bd = BIOSinfo.getDate();
           _bd = _bd.replaceAll(" ", "%20");
           //_bd = _bd.replaceAll(" ", "%28");
           //_bd = _bd.replaceAll(" ", "%2D");
           //_bd = _bd.replaceAll(" ", "%2E");

        String json = "{\"host_name\":\""+ip.getHostName()+"\",\"session_username\":\""+UserName+"\",\"ip_address\":\""+ip.getHostAddress()+"\",\"mac_address\":\""+sb.toString()+"\",\"os_name\":\""+_os+"\",\"os_type\":\""+osType+"\",\"cpu_name\":\""+_cpu+"\",\"gpu_name_1\":\""+_gpu_1+"\",\"gpu_name_2\":\""+_gpu_2+"\",\"storage_1\":\""+Storage_1+"\",\"storage_2\":\""+Storage_2+"\",\"storage_3\":\""+Storage_3+"\",\"ram_total\":\""+physicalMemorySize+"\",\"ram_avaliable\":\""+freePhysicalMemory+"\",\"free_swap_size\":\""+freeSwapSize+"\",\"bios_manufacturer\":\""+_bm+"\",\"bios_version\":\""+_bv+"\",\"bios_date\":\""+_bd+"\"}";

        // send as http get request
        URL url = new URL("http://localhost/test/mypc2inventory/api/get.php?catch="+json);
        URLConnection conn = url.openConnection();

            try (// Get the response
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = rd.readLine()) != null) {
                    System.out.println(line);
                }   }
            //rd.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        
        System.out.println("\n===== Process finished, thanks for collaborating ===== \n\n");
    
    }

}