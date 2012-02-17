#!/usr/bin/env python2.6

import nose, sys

sys.exit( nose.run( argv=[__file__, '--with-xunit', '-v','tests','-m', '(_t.py$)|(_t$)|(^test)'] ) )

