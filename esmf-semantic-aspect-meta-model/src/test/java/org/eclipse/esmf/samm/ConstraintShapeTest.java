/*
 * Copyright (c) 2023 Robert Bosch Manufacturing Solutions GmbH
 *
 * See the AUTHORS file(s) distributed with this work for additional
 * information regarding authorship.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package org.eclipse.esmf.samm;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.eclipse.esmf.samm.validation.SemanticError;

public class ConstraintShapeTest extends AbstractShapeTest {
   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testConstraintValidationExpectSuccess( final KnownVersion metaModelVersion ) {
      checkValidity( "constraint-shape", "TestConstraint", metaModelVersion );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testEmptyPropertiesExpectFailure2( final KnownVersion metaModelVersion ) {
      final SammUrns sammUrns = new SammUrns( metaModelVersion );

      final String constraintName = "TestConstraintWithEmptyProperties";
      final String constraintId = TEST_NAMESPACE_PREFIX + constraintName;
      final SemanticError resultForPreferredName = new SemanticError( MESSAGE_EMPTY_PROPERTY,
            constraintId, sammUrns.preferredNameUrn, VIOLATION_URN, "@en" );
      final SemanticError resultForDescription = new SemanticError( MESSAGE_EMPTY_PROPERTY,
            constraintId, sammUrns.descriptionUrn, VIOLATION_URN, "@en" );
      expectSemanticValidationErrors( "constraint-shape", constraintName,
            metaModelVersion, resultForPreferredName, resultForDescription );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testLanguageStringNotUniqueExpectFailure( final KnownVersion metaModelVersion ) {
      final SammUrns sammUrns = new SammUrns( metaModelVersion );

      final String constraintName = "TestConstraintNonUniqueLangStrings";
      final String constraintId = TEST_NAMESPACE_PREFIX + constraintName;
      final SemanticError resultForPreferredName = new SemanticError( MESSAGE_LANG_NOT_UNIQUE,
            constraintId, sammUrns.preferredNameUrn, VIOLATION_URN, "" );
      final SemanticError resultForDescription = new SemanticError( MESSAGE_LANG_NOT_UNIQUE,
            constraintId, sammUrns.descriptionUrn, VIOLATION_URN, "" );
      expectSemanticValidationErrors( "constraint-shape", constraintName, metaModelVersion, resultForPreferredName,
            resultForDescription );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testInvalidLanguageStringsExpectFailure( final KnownVersion metaModelVersion ) {
      final SammUrns sammUrns = new SammUrns( metaModelVersion );

      final String constraintName = "TestConstraintWithInvalidLangStrings";
      final String constraintId = TEST_NAMESPACE_PREFIX + constraintName;
      final SemanticError resultForPreferredName = new SemanticError(
            MESSAGE_INVALID_LANG_STRING, constraintId, sammUrns.preferredNameUrn, VIOLATION_URN, "Test Constraint" );
      final SemanticError resultForDescription = new SemanticError(
            MESSAGE_INVALID_LANG_STRING, constraintId, sammUrns.descriptionUrn, VIOLATION_URN, "TestConstraint" );
      expectSemanticValidationErrors( "constraint-shape", constraintName,
            metaModelVersion, resultForPreferredName, resultForDescription );
   }

   @ParameterizedTest
   @MethodSource( value = "allVersions" )
   public void testConstraintDefinesDataTypeExpectFailure( final KnownVersion metaModelVersion ) {
      final SammUrns sammUrns = new SammUrns( metaModelVersion );

      final String constraintName = "TestConstraintWithDataType";
      final String constraintId = TEST_NAMESPACE_PREFIX + constraintName;
      final SemanticError resultForDataType = new SemanticError( MESSAGE_MORE_THAN_ZERO_VALUES, constraintId,
            sammUrns.datatypeUrn, VIOLATION_URN, "" );
      expectSemanticValidationErrors( "constraint-shape", constraintName, metaModelVersion, resultForDataType );
   }
}
