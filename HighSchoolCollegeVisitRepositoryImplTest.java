package com.naviance.legacy.repository.school;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.naviance.legacy.TestContextConfig;
import com.naviance.legacy.domain.school.HighSchoolCollegeVisit;

/**
 * Tests for HighSchoolCollegeVisitRepository.
 * @author joby.job
 */

/*
making modifications in branch opy
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContextConfig.class })
@ActiveProfiles(profiles = "intTest")
public class HighSchoolCollegeVisitRepositoryImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private HighSchoolCollegeVisitRepository visitRep;

    @Test
    public void testGetCollegeVisitById() {
        int n = visitRep.getActiveRegistrationCountByStudentId(31L);
        assertThat(n, equalTo(3));
    }

    @Test
    public void testGetCollegeVisitsBySchoolIdAndCollegeId() {
        List<HighSchoolCollegeVisit> visits = visitRep.getCollegeVisitsBySchoolIdAndCollegeId(
            "9c21a37f-ef3d-9c81-56d6-26ded2948546", "e38979ee-1c53-11e6-b6ba-3e1d05defe79");
        assertThat(visits.size(), equalTo(2));
        HighSchoolCollegeVisit visit1 = visits.get(0);
        assertThat(visit1.getCollegeId(), equalTo("2002"));
        assertThat(visit1.getSchoolId(), equalTo("99999USPU"));
    }

    @Test
    public void testGetCollegeVisitsBySchoolId() {
        List<HighSchoolCollegeVisit> visits = visitRep.getCollegeVisitsBySchoolId(
            "9c21a37f-ef3d-9c81-56d6-26ded2948546");
        assertThat(visits.size(), equalTo(2));
        HighSchoolCollegeVisit visit1 = visits.get(0);
        assertThat(visit1.getCollegeId(), equalTo("2002"));
        assertThat(visit1.getSchoolId(), equalTo("99999USPU"));
    }

    @Test
    public void testGetActiveRegistrationsByStudentIdAndCollegeId() {
        List<Long> registeredVisitsList = visitRep.getActiveRegistrationsByStudentIdAndCollegeId(31L,
            "e38979ee-1c53-11e6-b6ba-3e1d05defe79");
        assertThat(registeredVisitsList.size(), equalTo(2));
        assertThat(registeredVisitsList.get(0), equalTo(18071L));
    }

    @Test
    public void testGetActiveRegistrationCountByStudentId() {
        Integer registrations = visitRep.getActiveRegistrationCountByStudentId(31L);
        assertThat(registrations, equalTo(3));
    }

    @Test
    public void testGetAttendeeCount() {
        Integer registrations = visitRep.getAttendeeCount(18071L);
        assertThat(registrations, equalTo(2));
    }

    @Test
    public void testRegisterStudent() {
        visitRep.registerStudent(18071L, 12L);
        Integer registrations = visitRep.getAttendeeCount(18071L);
        assertThat(registrations, equalTo(3));
    }

    @Test
    public void testUnRegisterStudent() {
        visitRep.unregisterStudent(18071L, 31L);
        Integer registrations = visitRep.getAttendeeCount(18071L);
        assertThat(registrations, equalTo(1));
    }

    @Test(expected = NoResultException.class)
    public void testUnRegisterStudentExpectedException() {
        visitRep.unregisterStudent(18071L, 1222121212L);
    }
}
