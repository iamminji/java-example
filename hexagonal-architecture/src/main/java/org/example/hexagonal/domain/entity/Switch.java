package org.example.hexagonal.domain.entity;

import org.example.hexagonal.domain.vo.IP;
import org.example.hexagonal.domain.vo.Network;
import org.example.hexagonal.domain.vo.SwitchId;
import org.example.hexagonal.domain.vo.SwitchType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Switch {

    private SwitchType switchType;
    private SwitchId switchId;
    private List<Network> networks;
    private IP address;

    public Switch(SwitchType switchType, SwitchId switchId, List<Network> networks, IP address) {
        this.switchType = switchType;
        this.switchId = switchId;
        this.networks = networks;
        this.address = address;
    }

    public Switch addNetwork(Network network) {
        var networks = new ArrayList<>(Arrays.asList(network));
        networks.add(network);
        // 새로운 Switch 객체를 만든다.
        return new Switch(this.switchType, this.switchId, networks, this.address);
    }

    public List<Network> getNetworks() {
        return networks;
    }
}
