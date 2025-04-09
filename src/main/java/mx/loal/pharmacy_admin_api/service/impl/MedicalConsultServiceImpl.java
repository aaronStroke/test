package mx.loal.pharmacy_admin_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.loal.pharmacy_admin_api.model.MedicalConsult;
import mx.loal.pharmacy_admin_api.payload.MedicalConsultDto;
import mx.loal.pharmacy_admin_api.repository.CustomerRepository;
import mx.loal.pharmacy_admin_api.repository.MedicalConsultRepository;
import mx.loal.pharmacy_admin_api.repository.RecipeRepository;
import mx.loal.pharmacy_admin_api.service.MedicalConsultService;
import mx.loal.pharmacy_admin_api.utils.printer.PrintUtility;
import mx.loal.pharmacy_admin_api.utils.reports.PdfUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MedicalConsultServiceImpl implements MedicalConsultService {

    private final CustomerRepository customerRepository;
    private final MedicalConsultRepository medicalConsultRepository;
    private final RecipeRepository recipeRepository;

    private final ModelMapper modelMapper;

    private final PdfUtils pdfUtils;

    @Value("${pdf.destination}")
    private String pdfDestination;

    @Transactional
    @Override
    public MedicalConsultDto save(Long customerId, MedicalConsultDto medicalConsultDto) {

        var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("El paciente no existe"));

        var medicalConsult = convertToEntity(medicalConsultDto);

        medicalConsult.setCustomer(customer);
        medicalConsult.setCreatedAt(LocalDateTime.now());

        medicalConsult = medicalConsultRepository.save(medicalConsult);

        try {
            var recipePdf = generatePDFRecipe(medicalConsult);
            var recipeDir = recipePdf.getAbsolutePath();

            recipeRepository.updateRecipeDir(medicalConsult.getRecipe().getId(), recipeDir);

        } catch (Exception e) {
            log.error("An error has occurred at recipe PDF generation", e);
        }

        return convertToDTO(medicalConsult);
    }

    private File generatePDFRecipe(MedicalConsult medicalConsult) throws Exception {

        Map<String, Object> params = new HashMap<>();
        Map<String, String> images = new HashMap<>();

        var recipeFileName = "Receta" + medicalConsult.getRecipe().getId();
        var finalRecipeName = pdfDestination + "/" + recipeFileName + ".pdf";

        var customerName = medicalConsult.getCustomer().getFirstName() +
            (Objects.nonNull(medicalConsult.getCustomer().getSecondName()) ? " " + medicalConsult.getCustomer().getSecondName() : "") + " " +
                medicalConsult.getCustomer().getLastName() + " " + medicalConsult.getCustomer().getSecondLastName();

        var treatment = medicalConsult.getRecipe()
            .getTreatments()
            .stream()
            .map(t -> {

                var activeSubstances = t.getProduct()
                    .getActiveSubstances()
                    .stream()
                    .map(a -> a.getSubstance().getName() + " " + a.getConcentration() + a.getMeasurementUnit().getIdentifier())
                    .collect(Collectors.joining(", "));

                return activeSubstances
                    .concat("<br />")
                    .concat(t.getIndications());
            })
            .collect(Collectors.joining("<br /><br />"));

        params.put("id", medicalConsult.getRecipe().getId());
        params.put("name", customerName);
        params.put("allergies", medicalConsult.getRecipe().getAllergies());
        params.put("recipeDate", medicalConsult.getCreatedAt().toLocalDate());
        params.put("age", medicalConsult.getCustomer().calculateAge());
        params.put("weight", medicalConsult.getRecipe().getWeight());
        params.put("size", medicalConsult.getRecipe().getSize());
        params.put("ta", medicalConsult.getRecipe().getTa());
        params.put("fc", medicalConsult.getRecipe().getFc());
        params.put("fr", medicalConsult.getRecipe().getFr());
        params.put("temperature", medicalConsult.getRecipe().getTemperature());
        params.put("treatment", treatment);

        List<MedicalConsult> beansList = new ArrayList<>();
        beansList.add(medicalConsult);

        images.put("unam.png", "LOGO_UNAM");
        images.put("ipn.png", "LOGO_IPN");
        images.put("back.png", "LOGO_BACK");

        var recipePdf = pdfUtils.generarPdf("recipe.jrxml", params, images, Collections.emptyMap(), recipeFileName, beansList);

        Files.move(recipePdf.toPath(), Paths.get(finalRecipeName), StandardCopyOption.REPLACE_EXISTING);

        recipePdf.deleteOnExit();

        PrintUtility.printPDFFile(finalRecipeName);

        return new File(finalRecipeName);
    }

    private MedicalConsult convertToEntity(MedicalConsultDto medicalConsultDto) {
        return modelMapper.map(medicalConsultDto, MedicalConsult.class);
    }

    private MedicalConsultDto convertToDTO(MedicalConsult medicalConsult) {
        return modelMapper.map(medicalConsult, MedicalConsultDto.class);
    }
}
