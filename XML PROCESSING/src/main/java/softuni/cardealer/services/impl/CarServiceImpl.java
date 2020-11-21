package softuni.cardealer.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.domain.dtos.xml.exportDtos.CarExportDto;
import softuni.cardealer.domain.dtos.xml.exportDtos.CarExportRootDto;
import softuni.cardealer.domain.dtos.xml.exportDtos.PartExportDto;
import softuni.cardealer.domain.dtos.xml.exportDtos.PartExportRootDto;
import softuni.cardealer.domain.dtos.xml.importDtos.CarImportDto;
import softuni.cardealer.domain.dtos.xml.importDtos.CarImportRootDto;
import softuni.cardealer.domain.entities.Car;
import softuni.cardealer.domain.entities.Part;
import softuni.cardealer.domain.repositories.CarRepository;
import softuni.cardealer.domain.repositories.PartRepository;
import softuni.cardealer.services.CarService;
import softuni.cardealer.utils.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {
    private static final String CAR_XML = "src/main/resources/xmls/cars.xml";
    private static final String CAR_AND_PARTS_EXPORT = "src/main/resources/exported/cars-and-parts.xml";
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final PartRepository partRepository;
    private final XmlParser xmlParser;


    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, Gson gson, PartRepository partRepository, XmlParser xmlParser) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.partRepository = partRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    @Transactional
    public void seedCars() throws Exception {
        CarImportRootDto carImportRootDto = this.xmlParser.parseXml(CarImportRootDto.class, CAR_XML);
        for (CarImportDto car : carImportRootDto.getCars()) {
            Car carEntity = this.modelMapper.map(car, Car.class);
            carEntity.setParts(this.getRandomParts());
            this.carRepository.saveAndFlush(carEntity);
        }

    }

    @Override
    public void carsAndParts() throws JAXBException {
        List<Car> allCars = this.carRepository.findAll();
        CarExportRootDto root = new CarExportRootDto();
        List<CarExportDto> carExportDtos = new ArrayList<>();
        for (Car car : allCars) {
            CarExportDto carExportDto = this.modelMapper.map(car, CarExportDto.class);
            PartExportRootDto partExportRootDto = new PartExportRootDto();
            List<PartExportDto> partExportDtos = new ArrayList<>();
            for (Part part : car.getParts()) {
                PartExportDto partDto = this.modelMapper.map(part, PartExportDto.class);
                partExportDtos.add(partDto);
            }
            partExportRootDto.setParts(partExportDtos);
            carExportDto.setParts(partExportRootDto);
            carExportDtos.add(carExportDto);
        }
        root.setCars(carExportDtos);
        this.xmlParser.exportToXml(root, CarExportRootDto.class, CAR_AND_PARTS_EXPORT);
    }


    private Set<Part> getRandomParts() throws Exception {
        Set<Part> parts = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Part part = this.getRandomPart();
            parts.add(part);
        }
        return parts;
    }

    private Part getRandomPart() throws Exception {
        Random random = new Random();
        long index = random.nextInt((int) this.partRepository.count()) + 1;
        Optional<Part> part = this.partRepository.findById(index);
        if (part.isPresent()) {
            return part.get();
        } else {
            throw new Exception("Invalid part id.");
        }
    }
}
