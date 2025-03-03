/* eslint-disable import/no-extraneous-dependencies */
import ClientPlugin from '@hilla/generator-typescript-plugin-client';
import snapshotMatcher from '@hilla/generator-typescript-utils/testing/snapshotMatcher.js';
import { expect, use } from 'chai';
import path from 'path';
import sinonChai from 'sinon-chai';
import BackbonePlugin from '../../src/index.js';
import { createGenerator, loadInput } from '../utils/common.js';

use(sinonChai);
use(snapshotMatcher);

describe('BackbonePlugin', () => {
  context('when a custom client file is available', () => {
    const sectionName = 'CustomClient';

    it('correctly generates code', async () => {
      const generator = createGenerator([BackbonePlugin, ClientPlugin], path.join(import.meta.url, '../fixtures'));
      const input = await loadInput(sectionName, import.meta.url);
      const files = await generator.process(input);
      expect(files.length).to.equal(1);

      const [endpointFile] = files;
      await expect(await endpointFile.text()).toMatchSnapshot(`${sectionName}Endpoint`, import.meta.url);
      expect(endpointFile.name).to.equal(`${sectionName}Endpoint.ts`);
    });
  });
});
