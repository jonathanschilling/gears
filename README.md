# Gearbox Calculator
This Gearbox Calculator can find compatible toothcounts for given transmission ratios.

Gearboxes designed with axially shifted metric (modulus) spur gears
must have the same sum of tooth counts for all transmission options on a common shaft.
Assume you want to be able to shift between to ratios `n1/m1` and `n2/m2`.
Then it is required to find tooth counts such that `n1+m1 = n2+m2`.

The program provided in this repository can do this for you automatically.
A suitable range of tooth count sums is specified,
by default ranging from `minToothSum=2` to `maxToothSum=200`.
The `toothSum` is scanned from `minToothSum` to `maxToothSum`.
For each tooth sum, a set of combinations of tooth counts
for all requested transmission ratios is searched for.
A given combination is tested to be realizable by scanning the tooth count of the first gear, `toothCount1`,
from `minToothSum-1` to the current `toothSum`.
The tooth count of the second gear is computed to yield the current `toothSum`:
`toothCount2 = toothSum - toothCount1`.
If the ratio `toothCount1/toothCount2` is compatible with the requested transmission ratio `n/m`,
it is accepted as a valid realization of this transmission ratio.
If realizations can be found for the whole set of requested transmission ratios
for a given `toothSum`, this is accepted as a valid solution.

## Usage

This program is a commandline utility.
The inputs are specified via an input file in [`JSON`](https://www.json.org) format:

```json
{
  "transmissionRatios": [ [2,1],
                          [1,1],
                          [2,5] ],
   "maxToothSum": 100,
   "minToothSum": 20,
   "outputTeethCounts": true
}
```

The `transmissionRatios` input parameter defines the list of transmission ratios
for which to find tooth count ratios.
Each entry is of the form `[n, m]`, e.g., `[ [n1, m1], [n2, m2] ]` for the example in the description above.
This parameter must be specified.

The parameters `maxToothSum` and `minToothSum` specify the range of tooth sums to be considered.
The default `maxToothSum` is 200 if it is not specified.
The default `minToothSum` is   2 if it is not specified.

The parameter `outputTeethCounts` is `false` by default.
It can be set to `true` in order to have the program output
concrete tooth counts if a solution was found.

A `JSON` output file will be created, where the input filename `/path/to/input.json`
is used to get the output filename: `/path/to/input_out.json`.

The output file contents are as follows (for above example):

```json
{
  "solution": [ [2,1],
                [1,1],
                [2,5] ],
  "teethCounts": [ [ [28,14],
                     [21,21],
                     [12,30] ],
                   [ [56,28],
                     [42,42],
                     [24,60] ] ]
}
```

A unique solution was found. It matches the requested `transmissionRatios` specified in the input.
Concrete realizations are listed, because `outputTeethCounts` was set to `true` in the input.
Two realizations are found, with teeth sums of 42 and 84.

## Download and Maven

The binary can be downloaded directly from this repository: [gears-1.0.0.jar](https://github.com/jonathanschilling/gears/releases/download/v1.0.0/gears-1.0.0.jar)

This program is also available on Maven Central for usage in other programs:

```xml
<dependency>
  <groupId>de.labathome</groupId>
  <artifactId>gears</artifactId>
  <version>1.0.0</version>
</dependency>
```
