if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
  echo -e "Starting gh-pages update\n"

  #copy data we're interested in to other place
  for project in 'core', 'groovy', 'guice', 'swing', 'javafx', 'pivot', 'lanterna'
  do
      rm -rf $HOME/travis-ci/reports/griffon-${project}
      mkdir -p $HOME/travis-ci/reports/griffon-${project}
      if [ -d $TRAVIS_BUILD_DIR/subprojects/griffon-${project}/build/reports/]; then
          echo -e "Copying $TRAVIS_BUILD_DIR/subprojects/griffon-${project}/build/reports"
          cp -R $TRAVIS_BUILD_DIR/subprojects/griffon-${project}/build/reports/ $HOME/travis-ci/reports/griffon-${project}
      fi
  done
  for plugin in 'datasource', 'theme', 'preferences', 'shiro', 'tasks'
  do
      rm -rf $HOME/travis-ci/reports/griffon-${plugin}-plugin/
      mkdir -p $HOME/travis-ci/reports/griffon-${plugin}-plugin/
      if [ -d $TRAVIS_BUILD_DIR/plugins/griffon-${plugin}-plugin/build/reports/]; then
          echo -e "$TRAVIS_BUILD_DIR/plugins/griffon-${plugin}-plugin/build/reports"
          cp -R $TRAVIS_BUILD_DIR/plugins/griffon-${plugin}-plugin/build/reports/ $HOME/travis-ci/reports/griffon-${plugin}-plugin/
      fi
  done

  #go to home and setup git
  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis"

  #using token clone gh-pages branch
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/aalmiray/griffon2.git gh-pages > /dev/null

  #go into directory and copy data we're interested in to that directory
  cd gh-pages
  cp -Rf $HOME/travis-ci/* .

  #add, commit and push files
  git add -f .
  git commit -m "Travis build $TRAVIS_BUILD_NUMBER pushed to gh-pages"
  git push -fq origin gh-pages > /dev/null

  echo -e "Done saving test reports\n"
fi