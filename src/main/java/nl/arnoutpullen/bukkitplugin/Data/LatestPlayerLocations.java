package nl.arnoutpullen.bukkitplugin.Data;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LatestPlayerLocations {

    private final Map<UUID, Location> locations = new HashMap<UUID, Location>();

    public Location insertOrUpdate(UUID uuid, Location location) {
        // Check if player already has location known
        Location userLocation = this.locations.get(uuid);

        if (userLocation == null) {
            return this.locations.put(uuid, location);
        } else {
            return this.locations.replace(uuid, location);
        }
    }

    public Location get(UUID uuid) {
        return this.locations.get(uuid);
    }

    public Location remove(UUID uuid) {
        return this.locations.remove(uuid);
    }

    public void clear() {
        this.locations.clear();
    }
}
