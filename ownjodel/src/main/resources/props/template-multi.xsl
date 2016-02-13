<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4-portrait"
					page-height="29.7cm" page-width="21.0cm" margin="2cm">
					<fo:region-body />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="A4-portrait">
				<fo:flow flow-name="xsl-region-body">
					<xsl:for-each select="poslist">
						<fo:block font-weight="bold" font-size="18pt" role="h1"
							space-after="3mm">
							<xsl:value-of select="post/title" />

						</fo:block>
						<fo:block space-after="2mm">
							<xsl:value-of select="post/content" />
						</fo:block>
						<fo:block font-size="14pt" role="h2" space-before="5mm"
							space-after="2mm" font-weight="bold">Votes</fo:block>
						<fo:block>
							<fo:table table-layout="fixed" width="100%">
								<fo:table-column column-width="70%" />
								<fo:table-column />
								<fo:table-header space-after="3mm">
									<fo:table-row>
										<fo:table-cell>
											<fo:block font-weight="bold">SessionId</fo:block>
										</fo:table-cell>
										<fo:table-cell>
											<fo:block font-weight="bold">Upvote?</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-header>
								<fo:table-body>
									<fo:table-row padding-after="2mm">
										<fo:table-cell>
											<fo:block />
										</fo:table-cell>
										<fo:table-cell>
											<fo:block />
										</fo:table-cell>
									</fo:table-row>
									<xsl:for-each select="post/votes">
										<fo:table-row padding-after="2mm">
											<fo:table-cell>
												<fo:block>
													<xsl:value-of select="jsessionid" />
												</fo:block>
											</fo:table-cell>
											<fo:table-cell>
												<fo:block>
													<xsl:value-of select="up" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>
							</fo:table>
						</fo:block>
					</xsl:for-each>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
</xsl:stylesheet>