package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer c=customerRepository2.findById(customerId).get();
            customerRepository2.delete(c);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver>drivers=driverRepository2.findAll();
		Collections.sort(drivers, (a, b)->a.getDriverId()-b.getDriverId());
		Driver d=null;
		for(Driver e:drivers){
			if(e.getCab().getAvailable()){
				d=e;
				break;
			}
		}
		if(d==null){
			throw new Exception("No cab available!");
		}

		TripBooking tripBooking=new TripBooking();
		tripBooking.setCustomer(customerRepository2.findById(customerId).get());
		tripBooking.setDriver(d);
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setBill(d.getCab().getPerKmRate()*distanceInKm);
		tripBooking.setStatus(TripStatus.CONFIRMED);

		d.getTripBookingList().add(tripBooking);
		driverRepository2.save(d);

		Customer c=new Customer();
		c.getTripBookings().add(tripBooking);
		customerRepository2.save(c);

		tripBookingRepository2.save(tripBooking);
		return tripBooking;

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking t=tripBookingRepository2.findById(tripId).get();
		t.setStatus(TripStatus.CANCELED);
		t.getDriver().getCab().setAvailable(true);
		t.setBill(0);
		t.setFromLocation(null);
		t.setFromLocation(null);
		t.setDistanceInKm(0);
		tripBookingRepository2.save(t);
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingl
		TripBooking trip=tripBookingRepository2.findById(tripId).get();
		trip.setStatus(TripStatus.COMPLETED);
		tripBookingRepository2.save(trip);

	}
}
