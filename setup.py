#!/usr/bin/env python2.6

import nose, sys, pickle
from optparse import OptionParser
parser = OptionParser()
parser.add_option("--test-total-slices", dest="total_slices",
                  help="number of slices we'll have", default = 1)
parser.add_option("--test-current-slice", dest="current_slice",
                  help="this slice", default = 0)

(options, args) = parser.parse_args()

print "This is the sandbox test harness. play nice"
print "Processing slice id %s of %s total" % (options.current_slice, options.total_slices)
retval = nose.run( argv=[__file__, '-q', 'tests', '-m',  '(_t.py$)|(_t$)|(^test)', '--collect-only', '--with-id'] )
if not retval:
    print "Failed to run nose.collectIds"
    sys.exit(1)

idhandle = open( ".noseids", "r" )
testIds = pickle.load(idhandle)['ids']
idhandle.close()

totalCases = len(testIds)
myIds      = []
for id in sorted( testIds.keys() ):
    if ( id % int(options.total_slices) ) == int(options.current_slice):
        myIds.append( str(id) )

print "Out of %s cases, we will run %s" % (totalCases, len(myIds))
if not myIds:
    sys.exit(0)

argv = [__file__, '--with-xunit', '-v','tests','-m', '(_t.py$)|(_t$)|(^test)', '--with-id']
argv.extend(myIds)
retval = nose.run( argv=argv )
print "Got the following return value: %s" % retval
if retval:
    sys.exit(0)
else:
    sys.exit(1)

