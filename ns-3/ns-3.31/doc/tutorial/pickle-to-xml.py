#!/usr/bin/python


# output xml format:
# <pages>
# <page url="xx"><prev url="yyy">zzz</prev><next url="hhh">lll</next><fragment>file.frag</fragment></page>
# ...
# </pages>

import pickle
import os
import codecs

def dump_pickles(out, dirname, filename, path):
    f = open(os.path.join(dirname, filename), 'r')
    api.data = pickle.load(f)
    fragment_file = codecs.open(api.data['current_page_name'] + '.frag', mode='w', encoding='utf-8')
    fragment_file.write(api.data['body'])
    fragment_file.close()
    out.write('  <page url="%s">\n' % path)
    out.write('    <fragment>%s.frag</fragment>\n' % api.data['current_page_name'])
    if api.data['prev'] is not None:
        out.write('    <prev url="%s">%s</prev>\n' % 
                  (os.path.normpath(os.path.join(path, api.data['prev']['link'])),
                   api.data['prev']['title']))
    if api.data['next'] is not None:
        out.write('    <next url="%s">%s</next>\n' % 
                  (os.path.normpath(os.path.join(path, api.data['next']['link'])),
                   api.data['next']['title']))
    out.write('  </page>\n')
    f.close()
    if api.data['next'] is not None:
        next_path = os.path.normpath(os.path.join(path, api.data['next']['link']))
        next_filename = os.path.basename(next_path) + '.fpickle'
        dump_pickles(out, dirname, next_filename, next_path)
    return

import sys

sys.stdout.write('<pages>\n')
dump_pickles(sys.stdout, os.path.dirname(sys.argv[1]), os.path.basename(sys.argv[1]), '/')
sys.stdout.write('</pages>')
