package com.CSIT321.DeliverYey.Controller;

import com.CSIT321.DeliverYey.Entity.DeliveryEntity;
import com.CSIT321.DeliverYey.Service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @PostMapping
    public DeliveryEntity insertDelivery(@RequestBody DeliveryEntity delivery) {
        return deliveryService.insertDelivery(delivery);
    }

    @GetMapping
    public List<DeliveryEntity> getAllDelivery(){
        return deliveryService.getAllDelivery();
    }

    @PutMapping
    public DeliveryEntity updateDelivery(@RequestParam Long deliveryId, @RequestBody DeliveryEntity newDeliveryDetails){
        return deliveryService.updateDelivery(deliveryId, newDeliveryDetails);
    }

    @DeleteMapping
    public String deleteDelivery(@RequestBody Long deliveryId){
        return deliveryService.deleteDelivery(deliveryId);
    }
}
