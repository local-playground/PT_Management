package org.rssb.phonetree.services;

import org.junit.Test;
import org.rssb.phonetree.ApplicationSetup;
import org.rssb.phonetree.common.file.DocumentWriter;
import org.rssb.phonetree.common.file.DocumentWriterFactory;
import org.rssb.phonetree.common.file.ReportFormat;
import org.rssb.phonetree.common.file.ReportName;
import org.rssb.phonetree.common.file.ReportType;
import org.rssb.phonetree.domain.SevadarVacationPlan;
import org.rssb.phonetree.domain.VacationDate;
import org.rssb.phonetree.entity.SevadarVacation;
import org.rssb.phonetree.helper.VacationPlanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "org.rssb.phonetree")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class SevadarVacationServiceTest extends ApplicationSetup {

    @Autowired
    private SevadarVacationService sevadarVacationService;

    @Autowired
    private DocumentWriterFactory documentWriterFactory;

    @Test
    public void getSevadarVacationBySevadarId(){
        Optional<SevadarVacation> sevadarVacation = sevadarVacationService.getSevadarVacationBySevadarId(26);
        if(sevadarVacation.isPresent()){
            System.out.println(sevadarVacation.get());
            List<VacationDate> vacationDateList = sevadarVacation.get().getVacationDateList();

            for(VacationDate vacationDate:vacationDateList){
               /*Period period = Period.between(vacationDate.getFromDate(),vacationDate.getToDate());
                System.out.print("Duration between "+vacationDate + " "+
                " Years = "+period.getYears()+
                " Months = "+period.getMonths()+
                " days ="+period.getDays()+"\n");*/

                System.out.println("Chorono Unit days = "+ ChronoUnit.DAYS.between(vacationDate.getFromDate(),vacationDate.getToDate()));
            }

            LocalDate startMonth = LocalDate.of(2017, 12, 01);
            LocalDate endMonth = LocalDate.of(2018, 03, 01);
            System.out.println(VacationPlanHelper.
                    getSevadarAvailabilityDetails(sevadarVacation.get().getSevadar().getSevadarName(),
                            vacationDateList, startMonth,endMonth));
        }else{
            System.out.println("Nothing found");
        }
    }

    @Test
    public void getTeamLeadVacationByTeamLeadId(){
        Optional<SevadarVacation> sevadarVacation = sevadarVacationService.getSevadarVacationByTeamLeadId(6);
        if(sevadarVacation.isPresent()){
            System.out.println(sevadarVacation.get());
        }else{
            System.out.println("Nothing found");
        }
    }

    @Test
    public void getAllSevadarsVacationPlan(){
        LocalDate startMonth = LocalDate.of(2017, 12, 01);
        LocalDate endMonth = LocalDate.of(2018, 02, 01);
        Map<String,List<SevadarVacationPlan>> vacationPlan=
                sevadarVacationService.getAllSevadarsVacationPlan(startMonth,endMonth);
        System.out.println(vacationPlan);
    }

    @Test
    public void writeVacationSummaryinWordDocument(){
        LocalDate startMonth = LocalDate.of(2017, 12, 01);
        LocalDate endMonth = LocalDate.of(2018, 02, 01);
        Map<String,List<SevadarVacationPlan>> vacationPlan=
                sevadarVacationService.getAllSevadarsVacationPlan(startMonth,endMonth);

        ReportType reportType = new ReportType();
        reportType.setReportFormat(ReportFormat.WORD);
        reportType.setReportName(ReportName.VACATION_SUMMARY);

        DocumentWriter documentWriter = documentWriterFactory.getDocumentWriter(reportType).get();
        documentWriter.writeToFile(vacationPlan);
    }
}
