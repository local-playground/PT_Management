package org.rssb.phonetree.controller.vacationplan;

import org.rssb.phonetree.controller.AbstractController;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Lazy
public class VacationPlanController extends AbstractController{
    private List<VacationPlanTemplateController> vacationPlanTemplateControllerList = new ArrayList<>();
}
