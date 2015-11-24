package pl.mobileTraveler.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import pl.mobileTraveler.scanner.Examples;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;


@Controller
public class ServerController {
    @RequestMapping(value = "/traveler", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Integer> sayHello(
            @RequestParam(value = "latitude", required = true) Double latitude,
            @RequestParam(value = "longitude", required = true) Double longitude,
            @RequestParam(value = "tag", required = true) String tag)
            throws IOException, SAXException, ParserConfigurationException {
        return Examples.temp(latitude, longitude, tag);
    }

    @RequestMapping(value = "/traveler2", method = RequestMethod.POST)
    @ResponseBody
    public boolean results(@RequestBody String coordinates) throws IOException {
        File file = new File(generatePathname());
        file.getParentFile().mkdirs();

        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.println("{\n"+coordinates+"\n}");
        writer.close();

        return true;
    }

    private String generatePathname() {
        return "coordinates/coordinates_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss_SSSS")) + ".txt";
    }
}
