package com.james.initializr.mapstruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.initializr.mapstruct.B_nestedmap.dto.FishTankDto;
import com.james.initializr.mapstruct.B_nestedmap.dto.FishTankWithNestedDocumentDto;
import com.james.initializr.mapstruct.B_nestedmap.mapper.FishTankMapper;
import com.james.initializr.mapstruct.B_nestedmap.mapper.FishTankMapperConstant;
import com.james.initializr.mapstruct.B_nestedmap.mapper.FishTankMapperExpression;
import com.james.initializr.mapstruct.B_nestedmap.mapper.FishTankMapperWithDocument;
import com.james.initializr.mapstruct.B_nestedmap.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Slf4j
public class B_NestedMappingTests {

	@Test
	public void shouldAutomapAndHandleSourceAndTargetPropertyNesting() {

		// -- prepare
		FishTank source = createFishTank();

		// -- action
		FishTankDto target = FishTankMapper.INSTANCE.map( source );

		// -- result
		assertThat( target.getName() ).isEqualTo( source.getName() );

		// fish and fishDto can be automapped
		assertThat( target.getFish() ).isNotNull();
		assertThat( target.getFish().getKind() ).isEqualTo( source.getFish().getType() );
		assertThat( target.getFish().getName() ).isNull();

		// automapping takes care of mapping property "waterPlant".
		assertThat( target.getPlant() ).isNotNull();
		assertThat( target.getPlant().getKind() ).isEqualTo( source.getPlant().getKind() );

		// ornament (nested asymetric source)
		assertThat( target.getOrnament() ).isNotNull();
		assertThat( target.getOrnament().getType() ).isEqualTo( source.getInterior().getOrnament().getType() );

		// material (nested asymetric target)
		assertThat( target.getMaterial() ).isNotNull();
		assertThat( target.getMaterial().getManufacturer() ).isNull();
		assertThat( target.getMaterial().getMaterialType() ).isNotNull();
		assertThat( target.getMaterial().getMaterialType().getType() ).isEqualTo( source.getMaterial().getType() );

		//  first symetric then asymetric
		assertThat( target.getQuality() ).isNotNull();
		assertThat( target.getQuality().getReport() ).isNotNull();
		assertThat( target.getQuality().getReport().getVerdict() )
				.isEqualTo( source.getQuality().getReport().getVerdict() );
		assertThat( target.getQuality().getReport().getOrganisation().getApproval() ).isNull();
		assertThat( target.getQuality().getReport().getOrganisation() ).isNotNull();
		assertThat( target.getQuality().getReport().getOrganisation().getName() )
				.isEqualTo( source.getQuality().getReport().getOrganisationName() );
	}

	@Test
	public void shouldAutomapAndHandleSourceAndTargetPropertyNestingReverse() {

		// -- prepare
		FishTank source = createFishTank();

		// -- action
		FishTankDto target = FishTankMapper.INSTANCE.map( source );
		FishTank source2 = FishTankMapper.INSTANCE.map( target );

		// -- result
		assertThat( source2.getName() ).isEqualTo( source.getName() );

		// fish
		assertThat( source2.getFish() ).isNotNull();
		assertThat( source2.getFish().getType() ).isEqualTo( source.getFish().getType() );

		// interior, designer will not be mapped (asymetric) to target. Here it shows.
		assertThat( source2.getInterior() ).isNotNull();
		assertThat( source2.getInterior().getDesigner() ).isNull();
		assertThat( source2.getInterior().getOrnament() ).isNotNull();
		assertThat( source2.getInterior().getOrnament().getType() )
				.isEqualTo( source.getInterior().getOrnament().getType() );

		// material
		assertThat( source2.getMaterial() ).isNotNull();
		assertThat( source2.getMaterial().getType() ).isEqualTo( source.getMaterial().getType() );

		// plant
		assertThat( source2.getPlant().getKind() ).isEqualTo( source.getPlant().getKind() );

		// quality
		assertThat( source2.getQuality().getReport() ).isNotNull();
		assertThat( source2.getQuality().getReport().getOrganisationName() )
				.isEqualTo( source.getQuality().getReport().getOrganisationName() );
		assertThat( source2.getQuality().getReport().getVerdict() )
				.isEqualTo( source.getQuality().getReport().getVerdict() );
	}

	@Test
	public void shouldAutomapAndHandleSourceAndTargetPropertyNestingAndConstant() {

		// -- prepare
		FishTank source = createFishTank();

		// -- action
		FishTankDto target = FishTankMapperConstant.INSTANCE.map( source );

		// -- result

		// fixed value
		assertThat( target.getFish().getName() ).isEqualTo( "Nemo" );

		// automapping takes care of mapping property "waterPlant".
		assertThat( target.getPlant() ).isNotNull();
		assertThat( target.getPlant().getKind() ).isEqualTo( source.getPlant().getKind() );

		// non-nested and constant
		assertThat( target.getMaterial() ).isNotNull();
		assertThat( target.getMaterial().getManufacturer() ).isEqualTo( "MMM" );
		assertThat( target.getMaterial().getMaterialType() ).isNotNull();
		assertThat( target.getMaterial().getMaterialType().getType() ).isEqualTo( source.getMaterial().getType() );

		assertThat( target.getOrnament() ).isNull();
		assertThat( target.getQuality() ).isNull();

	}

	@Test
	public void shouldAutomapAndHandleSourceAndTargetPropertyNestingAndExpresion() {

		// -- prepare
		FishTank source = createFishTank();

		// -- action
		FishTankDto target = FishTankMapperExpression.INSTANCE.map( source );

		// -- result
		assertThat( target.getFish().getName() ).isEqualTo( "Jaws" );

		assertThat( target.getMaterial() ).isNull();
		assertThat( target.getOrnament() ).isNull();
		assertThat( target.getPlant() ).isNull();

		assertThat( target.getQuality() ).isNotNull();
		assertThat( target.getQuality().getReport() ).isNotNull();
		assertThat( target.getQuality().getReport().getVerdict() )
				.isEqualTo( source.getQuality().getReport().getVerdict() );
		assertThat( target.getQuality().getReport().getOrganisation() ).isNotNull();
		assertThat( target.getQuality().getReport().getOrganisation().getApproval() ).isNull();
		assertThat( target.getQuality().getReport().getOrganisation().getName() ).isEqualTo( "Dunno" );
	}

	@Test
	public void shouldAutomapIntermediateLevelAndMapConstant() {

		// -- prepare
		FishTank source = createFishTank();

		// -- action
		FishTankWithNestedDocumentDto target = FishTankMapperWithDocument.INSTANCE.map( source );

		// -- result
		assertThat( target.getFish().getName() ).isEqualTo( "Jaws" );

		assertThat( target.getMaterial() ).isNull();
		assertThat( target.getOrnament() ).isNull();
		assertThat( target.getPlant() ).isNull();

		assertThat( target.getQuality() ).isNotNull();
		assertThat( target.getQuality().getDocument() ).isNotNull();
		assertThat( target.getQuality().getDocument().getVerdict() )
				.isEqualTo( source.getQuality().getReport().getVerdict() );
		assertThat( target.getQuality().getDocument().getOrganisation() ).isNotNull();
		assertThat( target.getQuality().getDocument().getOrganisation().getApproval() ).isNull();
		assertThat( target.getQuality().getDocument().getOrganisation().getName() ).isEqualTo( "NoIdeaInc" );
	}

	/**
	 *
	 * @return
	 * {
	 *     "fish": {
	 *         "type": "Carp"
	 *     },
	 *     "plant": {
	 *         "kind": "Water Hyacinth"
	 *     },
	 *     "name": "MyLittleFishTank",
	 *     "material": {
	 *         "type": "myMaterialType"
	 *     },
	 *     "interior": {
	 *         "designer": "MrVeryFamous",
	 *         "ornament": {
	 *             "type": "castle"
	 *         }
	 *     },
	 *     "quality": {
	 *         "report": {
	 *             "organisationName": "ACME",
	 *             "verdict": "PASSED"
	 *         }
	 *     }
	 * }
	 */
	private FishTank createFishTank()  {
		FishTank fishTank = new FishTank();

		Fish fish = new Fish();
		fish.setType( "Carp" );

		WaterPlant waterplant = new WaterPlant();
		waterplant.setKind( "Water Hyacinth" );

		Interior interior = new Interior();
		interior.setDesigner( "MrVeryFamous" );
		Ornament ornament = new Ornament();
		ornament.setType( "castle" );
		interior.setOrnament( ornament );

		WaterQuality quality = new WaterQuality();
		WaterQualityReport report = new WaterQualityReport();
		report.setVerdict( "PASSED" );
		report.setOrganisationName( "ACME" );
		quality.setReport( report );

		MaterialType materialType = new MaterialType();
		materialType.setType( "myMaterialType" );

		fishTank.setName( "MyLittleFishTank" );
		fishTank.setFish( fish );
		fishTank.setPlant( waterplant );
		fishTank.setInterior( interior );
		fishTank.setMaterial( materialType );
		fishTank.setQuality( quality );

		ObjectMapper mapper = new ObjectMapper();
		try {
			log.info("JSON: {}", mapper.writeValueAsString(fishTank));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return fishTank;
	}
}
