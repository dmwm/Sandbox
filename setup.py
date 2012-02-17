#!/usr/bin/env python2.6

import nose, sys

print "This is the sandbox test harness. play nice"

if nose.run( argv=[__file__, '--with-xunit', '-v','tests','-m', '(_t.py$)|(_t$)|(^test)'] ):
    sys.exit(0)
else:
    sys.exit(1)

