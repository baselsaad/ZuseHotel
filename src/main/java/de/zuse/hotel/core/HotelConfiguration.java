package de.zuse.hotel.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.zuse.hotel.util.ZuseCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Used for saving how many floors in hotel (floors contain how many rooms)
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class HotelConfiguration
{
    private String hotelName = "Zuse Hotel";
    private ArrayList<Floor> hotelFloors;
    private HashMap<String, Double> roomServices;

    public HotelConfiguration()
    {
        roomServices = new HashMap<>();
        hotelFloors = new ArrayList<>();
    }

    public ArrayList<Floor> getHotelFloors()
    {
        return hotelFloors;
    }

    public void setHotelFloors(ArrayList<Floor> hotelFloors)
    {
        this.hotelFloors = hotelFloors;
    }

    public void setDefaultFloorsAndRooms()
    {
        Floor floor = new Floor(1, 10);
        Floor floor2 = new Floor(2, 10);
        hotelFloors.add(floor);
        hotelFloors.add(floor2);

        addNewRoomService("Dinner", 20.0f);
        addNewRoomService("Wifi", 10.0f);
    }

    public void addNewFloor(Floor floor)
    {
        ZuseCore.check(hotelFloors.contains(floor) == false, "Floor is already in Hotel");

        hotelFloors.add(floor);
    }

    /**
     * @param floorNr - floor Number in hotelFloors
     * @param room    - room to add
     */
    public void addNewRoom(int floorNr, Room room)
    {
        ZuseCore.check(floorNr >= 0, "Floor number must be >= 0");

        //TODO(Basel): optimization
        for (Floor floor : hotelFloors)
        {
            if (floor.getFloorNr() == floorNr)
                floor.addRoom(room);
        }
    }

    public void removeRoom(int floorNr, int roomNr)
    {
        //TODO(Basel): optimization
        for (Floor hotelFloor : hotelFloors)
        {
            if (hotelFloor.getFloorNr() == floorNr)
            {
                for (int j = 0; j < hotelFloor.getRooms().size(); j++)
                {
                    if (hotelFloor.getRooms().get(j).getRoomNr() == roomNr)
                        hotelFloor.getRooms().remove(j);
                }
            }
        }
    }

    public Floor getFloorByFloorNr(int floorNr)
    {
        //TODO(Basel): optimization
        for (Floor floor : hotelFloors)
        {
            if (floor.getFloorNr() == floorNr)
                return floor;
        }

        return null;
    }

    public Room getRoomByRoomNr(int floorNr, int roomNr)
    {
        //TODO(Basel): optimization
        Floor floor = getFloorByFloorNr(floorNr);

        for (Room room : floor.getRooms())
        {
            if (room.getRoomNr() == roomNr)
                return room;
        }

        return null;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
    }

    public void addNewRoomService(String serviceName, double price)
    {
        ZuseCore.check(!roomServices.containsKey(serviceName),
                "a service with same name has already been added!");

        serviceName = serviceName.toLowerCase();
        roomServices.put(serviceName, price);
    }

    public double getRoomServicePrice(String serviceName)
    {
        serviceName = serviceName.toLowerCase();
        Double price = roomServices.get(serviceName);
        ZuseCore.check(price != null, "The Service Name is not valid!");

        return price;
    }

    @JsonIgnore
    public int getRoomServiceNum()
    {
        return roomServices.size();
    }

    public boolean hasServiceName(String name)
    {
        name = name.toLowerCase();
        return roomServices.get(name) != null;
    }

    public Map<String, Double> getRoomServices()
    {
        return roomServices;
    }

}
