package org.abimon.osl.drills.lin

import org.abimon.osl.OpenSpiralLanguageParser
import org.abimon.osl.SpiralDrillBit
import org.abimon.osl.drills.StaticDrill
import org.abimon.osl.drills.circuits.DrillCircuit
import org.abimon.osl.drills.headerCircuits.EvidenceSelectionDrill
import org.abimon.spiral.core.objects.scripting.lin.ChangeUIEntry
import org.abimon.spiral.core.objects.scripting.lin.ChoiceEntry
import org.abimon.spiral.core.objects.scripting.lin.LinScript
import org.parboiled.Action
import org.parboiled.Rule

/** Probably one of the more complicated codes */
object InternalLinEvidenceSelectionDrill : DrillCircuit {
    val DIGITS = "\\d+".toRegex()

    override fun OpenSpiralLanguageParser.syntax(): Rule =
            Sequence(
                    "[Internal] Select ",
                    FirstOf("Evidence", "Truth Bullet", "Truth Bullets"),
                    OptionalWhitespace(),
                    '{',
                    '\n',
                    Action<Any> { push(listOf(SpiralDrillBit(StaticDrill<LinScript>(ChangeUIEntry(35, 1)), ""))) },
                    OneOrMore(
                            FirstOf(
                                    Sequence(
                                            OptionalWhitespace(),
                                            ParameterToStack(),
                                            Action<Any> {
                                                val name = pop().toString().toLowerCase()
                                                return@Action name in EvidenceSelectionDrill.MISSING_ITEMS
                                            },
                                            Optional(
                                                    OptionalInlineWhitespace(),
                                                    "->"
                                            ),
                                            OptionalInlineWhitespace(),
                                            '{',
                                            '\n',
                                            Action<Any> { push(listOf(SpiralDrillBit(StaticDrill<LinScript>(ChoiceEntry(252)), ""))) },
                                            OpenSpiralLines(),
                                            OptionalWhitespace(),
                                            '}',
                                            '\n',
                                            OptionalWhitespace()
                                    ),
                                    Sequence(
                                            OptionalWhitespace(),
                                            ParameterToStack(),
                                            Action<Any> {
                                                val name = pop().toString().toLowerCase()
                                                return@Action name in EvidenceSelectionDrill.TIMEOUT
                                            },
                                            Optional(
                                                    OptionalInlineWhitespace(),
                                                    "->"
                                            ),
                                            OptionalInlineWhitespace(),
                                            '{',
                                            '\n',
                                            Action<Any> { push(listOf(SpiralDrillBit(StaticDrill<LinScript>(ChoiceEntry(253)), ""))) },
                                            OpenSpiralLines(),
                                            OptionalWhitespace(),
                                            '}',
                                            '\n',
                                            OptionalWhitespace()
                                    ),
                                    Sequence(
                                            OptionalWhitespace(),
                                            ParameterToStack(),
                                            Action<Any> {
                                                val name = pop().toString().toLowerCase()
                                                return@Action name in EvidenceSelectionDrill.OPENING_LINES
                                            },
                                            Optional(
                                                    OptionalInlineWhitespace(),
                                                    "->"
                                            ),
                                            OptionalInlineWhitespace(),
                                            '{',
                                            '\n',
                                            Action<Any> { push(listOf(SpiralDrillBit(StaticDrill<LinScript>(ChoiceEntry(254)), ""))) },
                                            OpenSpiralLines(),
                                            OptionalWhitespace(),
                                            '}',
                                            '\n',
                                            OptionalWhitespace()
                                    ),
                                    Sequence(
                                            OptionalWhitespace(),
                                            ItemID(),
                                            Action<Any> {
                                                val id = pop().toString().toIntOrNull() ?: 0
                                                return@Action push(listOf(SpiralDrillBit(StaticDrill<LinScript>(ChoiceEntry(id)), "")))
                                            },
                                            Optional(
                                                    OptionalInlineWhitespace(),
                                                    "->"
                                            ),
                                            OptionalInlineWhitespace(),
                                            '{',
                                            '\n',
                                            OpenSpiralLines(),
                                            OptionalWhitespace(),
                                            '}',
                                            '\n',
                                            OptionalWhitespace()
                                    )
                            )
                    ),
                    '}',
                    Action<Any> { push(listOf(SpiralDrillBit(StaticDrill<LinScript>(ChoiceEntry(255)), ""))) }
            )
}