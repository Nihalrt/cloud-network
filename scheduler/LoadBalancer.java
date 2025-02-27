package scheduler;

import core.VirtualMachine;
import java.util.*;

public class LoadBalancer {

    public static VirtualMachine getLeastLoadedVM(List<VirtualMachine> vms) {
        if (vms == null || vms.size() == 0) {
            return null;
        }
        VirtualMachine leastvm = vms.get(0);
        for (VirtualMachine vm : vms) {
            if(vm.getLoad() < leastvm.getLoad()){
                leastvm = vm;
            }
        }
        return leastvm;
    }

}
