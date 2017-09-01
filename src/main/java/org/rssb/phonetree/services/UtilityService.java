package org.rssb.phonetree.services;

import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.common.SevaType;

public interface UtilityService {
    Response isMemberAvailableForSeva(int memberId, SevaType sevaType);
    boolean hasAnyMemberInFamilyAssignedAnySeva(int familyId);
    boolean hasMemberAssignedAnySeva(int memberId);
}
